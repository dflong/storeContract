package com.dflong.storecontract.rest;

import com.dflong.storeapi.api.*;
import com.dflong.storeapi.api.act.Coupon;
import com.dflong.storeapi.api.act.Package;
import com.dflong.storeapi.api.base.StoreInfo;
import com.dflong.storeapi.api.billing.CreateContractBillingBo;
import com.dflong.storeapi.api.billing.CreateContractBillingDto;
import com.dflong.storeapi.api.contract.*;
import com.dflong.storeapi.api.goods.ServiceFee;
import com.dflong.storeapi.api.goods.VehicleModel;
import com.dflong.storeapi.api.goods.VehicleModelPrice;
import com.dflong.storeapi.api.user.DriverLicense;
import com.dflong.storeapi.api.user.User;
import com.dflong.storeapi.api.util.IDGenerator;
import com.dflong.storecontract.entity.*;
import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.entity.ContractItemInfo;
import com.dflong.storecontract.kafka.EventProducer;
import com.dflong.storecontract.kafka.KafkaConstant;
import com.dflong.storecontract.manage.DateUtils;
import com.dflong.storecontract.manage.ThreadPool;
import com.dflong.storecontract.rest.bo.CreateContractDB;
import com.dflong.storecontract.rest.dto.CreateContractDTO;
import com.dflong.storecontract.rpc.*;
import com.dflong.storecontract.transaction.ContractTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ContractService {

    public static final int lazyPayTime = 15; // 支付保留15分钟

    @Autowired
    private UserRpcServiceRpc userRpcServiceRpc;

    @Autowired
    private DriverLicenseRpcServiceRpc driverLicenseRpcServiceRpc;

    @Autowired
    private StoreInfoApiRpc storeInfoApiRpc;

    @Autowired
    private VehicleModelApiRpc vehicleModelApiRpc;

    @Autowired
    private VehicleStockApiRpc vehicleStockApiRpc;

    @Autowired
    private VehicleModelPriceApiRpc vehicleModelPriceApiRpc;

    @Autowired
    private ServiceFeeApiRpc serviceFeeApiRpc;

    @Autowired
    CouponRpcServiceRpc couponRpcServiceRpc;

    @Autowired
    PackageRpcServiceRpc packageRpcServiceRpc;

    @Autowired
    BillingApiRpc billingApiRpc;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ThreadPool threadPool;

    @Autowired
    private ContractQueryService queryService;

    @Autowired
    private ContractTransaction contractTransaction;

    @Autowired
    EventProducer eventProducer;

    public String createContract(CreateContractDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        long userId = dto.getUserId();

        // 下单前校验、组装billing参数
        CreateContractBillingDto billingDto = buildCreateContractBillingDto(dto, now);
        int startDay = DateUtils.dateToInt2(dto.getStartTime());
        int endDay = DateUtils.dateToInt2(dto.getEndTime());
        dto = null; // 及时释放内存

        // 计算费用
        CreateContractBillingBo billingBo = billingApiRpc.orderContractAmount(billingDto).join();
        billingDto = null; // 及时释放内存

        // 组装DB参数
        CreateContractDB createContractDB = buildContractDB(now, userId, billingBo);
        billingBo = null; // 及时释放内存

        Boolean deductStock = vehicleStockApiRpc.deductStock(createContractDB.getContractInfo().getVehicleModelId(), startDay, endDay, 1).join();
        if (!deductStock) {
            throw new ThrowException(-2506012, "create contract fail");
        }
        try {
            // 插入一条发送mq的消息任务，发送成功后删除，发送失败定时任务重试
            contractTransaction.createContract(createContractDB);
        } catch (Exception e) {
            vehicleStockApiRpc.releaseStock(createContractDB.getContractInfo().getVehicleModelId(), startDay, endDay, 1).join();
            throw new ThrowException(-2506012, e.getMessage());
        }
        // 发布消息
        eventProducer.send(KafkaConstant.CONTRACT_STATUS, createContractDB.getContractId(), ContractStatusEnum.PREPARING_VEHICLE.getCode() + "");
        String ongoingContractKey = "2506:ongoingContract:" + userId;
        redisTemplate.opsForValue().set(ongoingContractKey, ContractStatusEnum.PREPARING_VEHICLE.getCode() + "");
        return createContractDB.getContractId();
    }

    private CreateContractDB buildContractDB(LocalDateTime now, long userId, CreateContractBillingBo billingBo) {
        IDGenerator idGenerator = new IDGenerator(redisTemplate);

        String contractId = idGenerator.getContractId(now, userId + "");
        ContractInfo contractInfo = ContractInfo.build(contractId, now, userId, billingBo);

        String contractItemId = idGenerator.getContractItemId(now, userId + "");
        String payNo = idGenerator.getPayNo(now, userId + "");
        ContractItemInfo contractItemInfo = ContractItemInfo.build(contractItemId, now, billingBo, payNo, userId);

        List<String> deliveryPickUpIds = idGenerator.getDeliveryPickUpIds(now, userId + "", 2);// 预生成送取车id，避免后续生成订单时重复

        List<ContractDeliveryPickUpInfo> deliveryPickUpInfoList = new ArrayList<>();
        ContractDeliveryPickUpInfo deliveryInfo = null;
        if (contractInfo.getDeliveryVehicle() == ConstantStatus.SUCCESS.getCode()) {
            deliveryInfo = ContractDeliveryPickUpInfo.build(deliveryPickUpIds.get(0), contractId, billingBo.getStoreInfBo(), payNo, userId, now, PickUpTypeStatus.DELIVERY.getCode());
            deliveryPickUpInfoList.add(deliveryInfo);
        }

        ContractDeliveryPickUpInfo pickUpInfo = null;
        if (contractInfo.getPickUpVehicle() == ConstantStatus.SUCCESS.getCode()) {
            pickUpInfo = ContractDeliveryPickUpInfo.build(deliveryPickUpIds.get(1), contractId, billingBo.getStoreInfBo(), payNo, userId, now, PickUpTypeStatus.PICK_UP.getCode());
            deliveryPickUpInfoList.add(pickUpInfo);
        }

        List<ServiceFeeBo> serviceFeeBoList = billingBo.getServiceFeeBoList();
        List<String> detailIds = idGenerator.getDetailIds(now, userId + "", serviceFeeBoList.size());// 预生成服务费详情id，避免后续生成订单时重复
        List<ContractFeeDetailInfo> contractFeeDetailInfoList = ContractFeeDetailInfo.build(detailIds, contractItemId, contractId, userId, now, billingBo.getServiceFeeBoList(), deliveryInfo, pickUpInfo);

        List<ReduceFeeBo> reduceFeeBoList = billingBo.getReduceFeeBoList();
        List<String> reduceDetailIds = idGenerator.getReduceDetailIds(now, userId + "", reduceFeeBoList.size());
        List<ContractReduceDetailInfo> contractReduceDetailInfoList = ContractReduceDetailInfo.build(reduceDetailIds, contractItemId, contractId, userId, now, billingBo.getReduceFeeBoList());

        PayInfo payInfo = PayInfo.build(payNo, contractId, billingBo.getTotalAmount(), now, userId, lazyPayTime);

        CreateContractDB build = CreateContractDB.build(contractInfo, contractItemInfo,
                deliveryPickUpInfoList, contractFeeDetailInfoList, contractReduceDetailInfoList, payInfo);
        build.setContractId(contractId);
        return build;
    }

    private CreateContractBillingDto buildCreateContractBillingDto(CreateContractDTO dto, LocalDateTime now) {
        // 不校验参数

        long userId = dto.getUserId();

        // 是否有进行中订单
        int lastContractStatus = queryService.getLastContractStatus(userId);
        if (ContractStatusEnum.isRunningStatus(lastContractStatus)) {
            throw new ThrowException(-2504012, "User has ongoing contract");
        }

        // 查询用户id、驾驶证是否正常
        CompletableFuture<User> userInfoFuture = userRpcServiceRpc.getById(userId);
        CompletableFuture<DriverLicense> driverLicenseFuture = driverLicenseRpcServiceRpc.getByUserId(userId);

        CompletableFuture.allOf(userInfoFuture, driverLicenseFuture).thenRun(() -> {
            User userInfo = userInfoFuture.join();
            if (userInfo == null || userInfo.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 用户不存在或状态异常
                throw new ThrowException(-2504002, "User not found or status abnormal");
            }

            DriverLicense driverLicense = driverLicenseFuture.join();
            if (driverLicense == null || driverLicense.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 驾驶证不存在或状态异常
                throw new ThrowException(-2504003, "Driver license not found or status abnormal");
            }
            if (driverLicense.getExpireTime().isBefore(now)) {
                // 驾驶证过期
                throw new ThrowException(-2504004, "Driver license is expired");
            }
        })
        .exceptionally(ex -> {
            ex.getCause().printStackTrace();
            if (ex.getCause() instanceof ThrowException) {
                // 已经是自定义异常，直接抛出
                throw (ThrowException) ex.getCause();
            }
            throw new ThrowException(-2504001, ex.getMessage());
        })
        .join();

        // 取还门店信息
        long pickStoreId = dto.getPickStoreId();
        long returnStoreId = dto.getReturnStoreId();
        int deliveryVehicle = dto.getDeliveryVehicle();
        int pickUpVehicle = dto.getPickUpVehicle();

        CompletableFuture<StoreInfo> pickStoreInfoFuture = storeInfoApiRpc.getStoreInfoById(pickStoreId);
        CompletableFuture<StoreInfo> returnStoreInfoFuture = storeInfoApiRpc.getStoreInfoById(returnStoreId);

        StoreInfBo checkedStoreInfo =
        CompletableFuture.allOf(pickStoreInfoFuture, returnStoreInfoFuture).thenApplyAsync(res -> {
            StoreInfBo storeInfBo = new StoreInfBo();

            StoreInfo pickStoreInfo = pickStoreInfoFuture.join();
            if (pickStoreInfo == null || pickStoreInfo.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 取车门店不存在或状态异常
                throw new ThrowException(-2504005, "Pick-up store not found or status abnormal");
            }
            storeInfBo.setPickUpStoreId(pickStoreId);

            if (deliveryVehicle == ConstantStatus.SUCCESS.getCode()) {
                storeInfBo.setDeliveryVehicle(deliveryVehicle);
                storeInfBo.setDeliveryLongitude(dto.getDeliveryLongitude());
                storeInfBo.setDeliveryLatitude(dto.getDeliveryLatitude());
                Double deliveryDistance = storeInfoApiRpc.getDistance(dto.getDeliveryLongitude(), dto.getDeliveryLatitude(), pickStoreInfo.getLongitude(), pickStoreInfo.getLatitude())
                        .thenApply(distance -> distance).join();
                storeInfBo.setPickUpDistance(deliveryDistance);
                storeInfBo.setPickUpUnitPrice(BigDecimal.ONE); // 默认
            }

            StoreInfo returnStoreInfo = returnStoreInfoFuture.join();
            if (returnStoreInfo == null || returnStoreInfo.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 还车门店不存在或状态异常
                throw new ThrowException(-2504006, "Return store not found or status abnormal");
            }
            storeInfBo.setReturnStoreId(returnStoreId);
            if (pickUpVehicle == ConstantStatus.SUCCESS.getCode()) {
                storeInfBo.setPickUpVehicle(pickUpVehicle);
                storeInfBo.setPickUpLongitude(dto.getPickUpLongitude());
                storeInfBo.setPickUpLatitude(dto.getPickUpLatitude());
                Double pickUpDistance = storeInfoApiRpc.getDistance(dto.getPickUpLongitude(), dto.getPickUpLatitude(), returnStoreInfo.getLongitude(), returnStoreInfo.getLatitude())
                        .thenApply(distance -> distance).join();
                storeInfBo.setReturnDistance(pickUpDistance);
                storeInfBo.setReturnUnitPrice(BigDecimal.ONE); // 默认
            }

            return storeInfBo;
        }, threadPool.contractThreadPool())
        .exceptionally(ex -> {
            ex.getCause().printStackTrace();
            if (ex.getCause() instanceof ThrowException) {
                // 已经是自定义异常，直接抛出
                throw (ThrowException) ex.getCause();
            }
            throw new ThrowException(-2504001, ex.getMessage());
        })
        .join();

        // 车型、库存、价格、服务费
        long vehicleModelId = dto.getVehicleModelId();
        Date startTime = dto.getStartTime();
        Date endTime = dto.getEndTime();
        int startDay = DateUtils.dateToInt(startTime);
        int endDay = DateUtils.dateToInt(endTime);
        int dayDiffer = DateUtils.dayDiffer(startTime, endTime);
        CompletableFuture<VehicleModel> vehicleModelFuture = vehicleModelApiRpc.getById(vehicleModelId);
        CompletableFuture<Boolean> stockFuture = vehicleStockApiRpc.checkStock(vehicleModelId, DateUtils.dateToInt2(startTime), DateUtils.dateToInt2(endTime), 1);
        CompletableFuture<List<VehicleModelPrice>> vehicleModelPriceListFuture = vehicleModelPriceApiRpc.listCalendarPrice(vehicleModelId, startDay, endDay);
        CompletableFuture<List<ServiceFee>> serviceFeeListFuture = serviceFeeApiRpc.listByVehicleModel(vehicleModelId);

        HashMap<String, Object> vehicleMap =
        CompletableFuture.allOf(vehicleModelFuture, stockFuture, vehicleModelPriceListFuture, serviceFeeListFuture).thenApply(res -> {
            VehicleModel vehicleModel = vehicleModelFuture.join();
            if (vehicleModel == null || vehicleModel.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 车型不存在或状态异常
                throw new ThrowException(-2504007, "Vehicle model not found or status abnormal");
            }
            Boolean stock = stockFuture.join();
            if (!stock) {
                // 库存不足
                throw new ThrowException(-2504008, "Not enough stock for the vehicle model");
            }

            VehicleInfoBo vehicleInfoBo = new VehicleInfoBo();
            vehicleInfoBo.setVehicleModelId(vehicleModelId);
            vehicleInfoBo.setType(vehicleModel.getType());

            List<VehicleModelPrice> vehicleModelPriceList = vehicleModelPriceListFuture.join();
            if (vehicleModelPriceList == null || vehicleModelPriceList.size() < dayDiffer) {
                // 价格配置不足
                throw new ThrowException(-2504009, "Not enough price for the vehicle model");
            }

            List<VehicleModelPriceBo> modelPriceBos = vehicleModelPriceList.stream().map(price -> {
                VehicleModelPriceBo bo = new VehicleModelPriceBo();
                bo.setDay(price.getDay());
                bo.setPrice(price.getPrice());
                return bo;
            }).collect(Collectors.toList());

            List<ServiceFeeBo> serviceFeeBos = serviceFeeListFuture.join().stream().map(serviceFee -> {
                ServiceFeeBo serviceFeeBo = new ServiceFeeBo();
                serviceFeeBo.setFeeType(serviceFee.getFeeType());
                serviceFeeBo.setPrice(serviceFee.getPrice());
                serviceFeeBo.setIsForce(serviceFee.getIsForce());
                return serviceFeeBo;
            }).collect(Collectors.toList());

             HashMap<String, Object> infoMap = new HashMap<>();
             infoMap.put("vehicleInfoBo", vehicleInfoBo);
             infoMap.put("modelPriceBos", modelPriceBos);
             infoMap.put("serviceFeeBos", serviceFeeBos);

            return infoMap;
        })
        .exceptionally(ex -> {
        ex.getCause().printStackTrace();
        if (ex.getCause() instanceof ThrowException) {
            // 已经是自定义异常，直接抛出
            throw (ThrowException) ex.getCause();
        }
        throw new ThrowException(-2504001, ex.getMessage());
        })
        .join();

        VehicleInfoBo vehicleInfoBo = (VehicleInfoBo) vehicleMap.get("vehicleInfoBo");
        List<VehicleModelPriceBo> modelPriceBos = (List<VehicleModelPriceBo>) vehicleMap.get("modelPriceBos");
        List<ServiceFeeBo> serviceFeeBos = (List<ServiceFeeBo>) vehicleMap.get("serviceFeeBos");

        // 超时、取消 规则入表用

        // 营销
        long packageId = dto.getPackageId();
        long couponId = dto.getCouponId();

        CompletableFuture<Coupon> couponFuture = couponRpcServiceRpc.getById(couponId);
        CouponBo couponBo =
        couponFuture.thenApply(coupon -> {
            if (coupon == null || coupon.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 优惠券不存在或状态异常
                throw new ThrowException(-2504010, "Coupon not found or status abnormal");
            }
            CouponBo temp = new CouponBo();
            temp.setCouponId(coupon.getId());
            temp.setOffset(coupon.getOffset());
            return temp;
        })
        .exceptionally(ex -> {
            ex.getCause().printStackTrace();
            if (ex.getCause() instanceof ThrowException) {
                // 已经是自定义异常，直接抛出
                throw (ThrowException) ex.getCause();
            }
            throw new ThrowException(-2504001, ex.getMessage());
        })
        .join();

        CompletableFuture<Package> packageFuture = packageRpcServiceRpc.getById(packageId);
        PackageBo packageBo =
        packageFuture.thenApply(packages -> {
            if (packages == null || packages.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 套餐不存在或状态异常
                throw new ThrowException(-2504011, "Packages not found or status abnormal");
            }
            PackageBo temp = new PackageBo();
            temp.setPackageId(packages.getId());
            temp.setStartDay(packages.getStartDay());
            temp.setEndDay(packages.getEndDay());
            temp.setPrice(packages.getPrice());
            return temp;
        })
        .exceptionally(ex -> {
            ex.getCause().printStackTrace();
            if (ex.getCause() instanceof ThrowException) {
                // 已经是自定义异常，直接抛出
                throw (ThrowException) ex.getCause();
            }
            throw new ThrowException(-2504001, ex.getMessage());
        })
        .join();


        CreateContractBillingDto billingDto = new CreateContractBillingDto();
        billingDto.setUserId(userId);
        billingDto.setVehicleModelId(vehicleModelId);
        billingDto.setStartTime(startTime);
        billingDto.setEndTime(endTime);
        billingDto.setStartDay(startDay);
        billingDto.setEndDay(endDay);
        billingDto.setRentDay(dayDiffer);
        billingDto.setServiceIds(dto.getFeeIds());
        billingDto.setStoreInfBo(checkedStoreInfo);
        billingDto.setVehicleInfoBo(vehicleInfoBo);
        billingDto.setVehicleModelPriceBoList(modelPriceBos);
        billingDto.setServiceFeeBoList(serviceFeeBos);
        billingDto.setCouponBo(couponBo);
        billingDto.setPackageBo(packageBo);
        return billingDto;
    }

}
