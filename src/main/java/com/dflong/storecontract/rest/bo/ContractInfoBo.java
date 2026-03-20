package com.dflong.storecontract.rest.bo;

import com.dflong.storecontract.entity.ContractItemInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ContractInfoBo {

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
     * 车架号
     */
    private String vin;

    /**
     * 租车天数
     */
    private Integer rentDay;

    /**
     * 合同开始时间
     */
    private Date contractStartTime;

    /**
     * 合同结束时间
     */
    private Date contractEndTime;

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

    List<ContractItemInfo> contractItemInfos;

    public List<ContractItemInfo> getContractItemInfos() {
        return contractItemInfos;
    }

    public void setContractItemInfos(List<ContractItemInfo> contractItemInfos) {
        this.contractItemInfos = contractItemInfos;
    }

    // 构造函数
    public ContractInfoBo() {
    }

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
}