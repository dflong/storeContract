package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dflong.storeapi.api.ConstantStatus;
import com.dflong.storeapi.api.ContractStatusEnum;
import com.dflong.storeapi.api.billing.CreateContractBillingBo;
import com.dflong.storeapi.api.contract.StoreInfBo;
import com.dflong.storecontract.manage.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 合同信息实体类
 */
@TableName("contract_info")
public class ContractInfo {
    
    /**
     * 合同ID
     */
    @TableId(value = "contract_id", type = IdType.INPUT)
    private String contractId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 合同状态 1:备车中 2：待取车 3：用车中 4：还车中 5：已还车 6：已完成 7：已取消
     */
    private Integer status;
    
    /**
     * 合同金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 车型ID
     */
    private Long vehicleModelId;
    
    /**
     * 能源类型 1:纯油 2:混动 3:纯电 4:增程
     */
    private Integer energyType;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 租车天数
     */
    private Integer rentDay;

    /**
     * 费用类型
     */
    private String feeTypeIds;

    /**
     * 合同开始时间
     */
    private Date contractStartTime;
    
    /**
     * 合同结束时间
     */
    private Date contractEndTime;
    
    /**
     * 计费开始时间
     */
    private Date billingStartTime;
    
    /**
     * 计费结束时间
     */
    private Date billingEndTime;
    
    /**
     * 是否有送车上门服务 1：有 2：没有
     */
    private Integer deliveryVehicle;
    
    /**
     * 是否有上门取车服务 1：有 2：没有
     */
    private Integer pickUpVehicle;
    
    /**
     * 取车门店
     */
    private Long pickStoreId;

    /**
     * 还车门店
     */
    private Long returnStoreId;

    /**
     * 套餐id
     */
    private Long packageId = - 1L;

    /**
     * 优惠券id
     */
    private Long couponId = - 1L;

    /**
     * 是否删除 1：正常 2：已删除
     */
    private Integer isDelete;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 更新人
     */
    private String updateBy;

    // 构造函数
    public ContractInfo() {
    }

    public static ContractInfo build(String contractId, LocalDateTime now, long userId, CreateContractBillingBo createContractBillingBo) {
        ContractInfo contractInfo = new ContractInfo();

        contractInfo.setContractId(contractId);
        contractInfo.setUserId(userId);
        contractInfo.setStatus(ContractStatusEnum.PREPARING_VEHICLE.getCode());
        contractInfo.setTotalAmount(createContractBillingBo.getTotalAmount());
        contractInfo.setVehicleModelId(createContractBillingBo.getVehicleModelId());
//        contractInfo.setEnergyType(billingDto.getVehicleInfoBo().getType());
        contractInfo.setRentDay(createContractBillingBo.getRentDay());
        contractInfo.setFeeTypeIds(createContractBillingBo.getFeeTypeIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        contractInfo.setContractStartTime(createContractBillingBo.getStartTime());
        contractInfo.setContractEndTime(createContractBillingBo.getEndTime());
        StoreInfBo createStoreInfo = createContractBillingBo.getStoreInfBo();
        contractInfo.setDeliveryVehicle(createStoreInfo.getDeliveryVehicle());
        contractInfo.setPickUpVehicle(createStoreInfo.getPickUpVehicle());
        contractInfo.setPickStoreId(createStoreInfo.getPickUpStoreId());
        contractInfo.setReturnStoreId(createStoreInfo.getReturnStoreId());
        if (createContractBillingBo.getCouponId() > 0) {
            contractInfo.setCouponId(createContractBillingBo.getCouponId());
        }
        if (createContractBillingBo.getPackageId() > 0) {
            contractInfo.setPackageId(createContractBillingBo.getPackageId());
        }
        contractInfo.setIsDelete(ConstantStatus.SUCCESS.getCode());
        contractInfo.setCreateBy(userId + "");
        contractInfo.setCreateTime(DateUtils.fromLocalDateTime(now));
        contractInfo.setUpdateBy(userId + "");
        contractInfo.setUpdateTime(DateUtils.fromLocalDateTime(now));

        return contractInfo;
    }

    // getter和setter方法
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(Long vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public Integer getEnergyType() {
        return energyType;
    }

    public void setEnergyType(Integer energyType) {
        this.energyType = energyType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getRentDay() {
        return rentDay;
    }

    public void setRentDay(Integer rentDay) {
        this.rentDay = rentDay;
    }

    public String getFeeTypeIds() {
        return feeTypeIds;
    }

    public void setFeeTypeIds(String feeTypeIds) {
        this.feeTypeIds = feeTypeIds;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Date getBillingStartTime() {
        return billingStartTime;
    }

    public void setBillingStartTime(Date billingStartTime) {
        this.billingStartTime = billingStartTime;
    }

    public Date getBillingEndTime() {
        return billingEndTime;
    }

    public void setBillingEndTime(Date billingEndTime) {
        this.billingEndTime = billingEndTime;
    }

    public Integer getDeliveryVehicle() {
        return deliveryVehicle;
    }

    public void setDeliveryVehicle(Integer deliveryVehicle) {
        this.deliveryVehicle = deliveryVehicle;
    }

    public Integer getPickUpVehicle() {
        return pickUpVehicle;
    }

    public void setPickUpVehicle(Integer pickUpVehicle) {
        this.pickUpVehicle = pickUpVehicle;
    }

    public Long getPickStoreId() {
        return pickStoreId;
    }

    public void setPickStoreId(Long pickStoreId) {
        this.pickStoreId = pickStoreId;
    }

    public Long getReturnStoreId() {
        return returnStoreId;
    }

    public void setReturnStoreId(Long returnStoreId) {
        this.returnStoreId = returnStoreId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "ContractInfo{" +
                "contractId='" + contractId + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", vehicleModelId=" + vehicleModelId +
                ", energyType=" + energyType +
                ", vin='" + vin + '\'' +
                ", rentDay=" + rentDay +
                ", feeTypeIds='" + feeTypeIds + '\'' +
                ", contractStartTime=" + contractStartTime +
                ", contractEndTime=" + contractEndTime +
                ", billingStartTime=" + billingStartTime +
                ", billingEndTime=" + billingEndTime +
                ", deliveryVehicle=" + deliveryVehicle +
                ", pickUpVehicle=" + pickUpVehicle +
                ", pickStoreId=" + pickStoreId +
                ", returnStoreId=" + returnStoreId +
                ", packageId=" + packageId +
                ", couponId=" + couponId +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}