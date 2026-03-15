package com.dflong.storecontract.rest;

import com.dflong.storeapi.api.ConstantStatus;
import com.dflong.storeapi.api.ThrowException;
import com.dflong.storeapi.api.base.StoreInfo;
import com.dflong.storeapi.api.user.DriverLicense;
import com.dflong.storeapi.api.user.User;
import com.dflong.storeapi.api.user.UserRpcService;
import com.dflong.storecontract.manage.ThreadPool;
import com.dflong.storecontract.rpc.provider.DriverLicenseRpcServiceRpc;
import com.dflong.storecontract.rpc.provider.StoreInfoApiRpc;
import com.dflong.storecontract.rpc.provider.UserRpcServiceRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class ContractService {

    @Autowired
    private UserRpcServiceRpc userRpcServiceRpc;

    @Autowired
    private DriverLicenseRpcServiceRpc driverLicenseRpcServiceRpc;

    @Autowired
    private StoreInfoApiRpc storeInfoApiRpc;

    @Autowired
    ThreadPool threadPool;

    public String createContract(CreateContractDTO dto) {
//        StoreInfo pickUpStoreId = storeInfoApiRpc.getStoreInfoById(dto.getPickStoreId());
        // 不校验参数

        // 查询用户id、驾驶证是否正常
        LocalDateTime now = LocalDateTime.now();
        long userId = dto.getUserId();
        CompletableFuture<User> userInfoFuture = userRpcServiceRpc.getById(userId);
        CompletableFuture<DriverLicense> driverLicenseFuture = driverLicenseRpcServiceRpc.getByUserId(userId);

        CompletableFuture.allOf(userInfoFuture, driverLicenseFuture).thenRunAsync(() -> {
            User userInfo = userInfoFuture.join();
            if (userInfo == null || userInfo.getStatus() != ConstantStatus.SUCCESS.getCode()) {
                // 用户不存在或状态异常
                throw new ThrowException(-2504002, "User not found or status abnormal");
            }

            DriverLicense driverLicense = driverLicenseFuture.join();
            if (driverLicense == null || driverLicense.getStatus() != 1) {
                // 驾驶证不存在或状态异常
                throw new ThrowException(-2504003, "Driver license not found or status abnormal");
            }
            if (driverLicense.getExpireTime().isBefore(now)) {
                // 驾驶证过期
                throw new ThrowException(-2504003, "Driver license is expired");
            }
        }, threadPool.contractThreadPool()).exceptionally(ex -> {
            if (ex.getCause() instanceof ThrowException) {
                // 已经是自定义异常，直接抛出
                throw (ThrowException) ex.getCause();
            }
            throw new ThrowException(-2504001, ex.getMessage());
        }).join();


        // 门店信息
//        User join = CompletableFuture.allOf(userInfoFuture, driverLicenseFuture);




        return "success";
    }

}
