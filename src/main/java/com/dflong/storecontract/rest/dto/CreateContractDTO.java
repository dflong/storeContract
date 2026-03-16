package com.dflong.storecontract.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class CreateContractDTO {

    private long userId;

    private long vehicleModelId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 取车门店
     */
    private long pickStoreId;

    /**
     * 是否有送车上门服务 1：有 2：没有
     */
    private int deliveryVehicle;

    private double deliveryLongitude;

    private double deliveryLatitude;

    /**
     * 还车门店
     */
    private long returnStoreId;

    /**
     * 是否有上门取车服务 1：有 2：没有
     */
    private int pickUpVehicle;

    private double pickUpLongitude;

    private double pickUpLatitude;

    private List<Integer> feeIds;

    private long packageId;

    private long couponId;


    public List<Integer> getFeeIds() {
        return feeIds;
    }

    public void setFeeIds(List<Integer> feeIds) {
        this.feeIds = feeIds;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(long vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDeliveryVehicle() {
        return deliveryVehicle;
    }

    public void setDeliveryVehicle(int deliveryVehicle) {
        this.deliveryVehicle = deliveryVehicle;
    }

    public double getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(double deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    public double getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(double deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public int getPickUpVehicle() {
        return pickUpVehicle;
    }

    public void setPickUpVehicle(int pickUpVehicle) {
        this.pickUpVehicle = pickUpVehicle;
    }

    public double getPickUpLongitude() {
        return pickUpLongitude;
    }

    public void setPickUpLongitude(double pickUpLongitude) {
        this.pickUpLongitude = pickUpLongitude;
    }

    public double getPickUpLatitude() {
        return pickUpLatitude;
    }

    public void setPickUpLatitude(double pickUpLatitude) {
        this.pickUpLatitude = pickUpLatitude;
    }

    public long getPickStoreId() {
        return pickStoreId;
    }

    public void setPickStoreId(long pickStoreId) {
        this.pickStoreId = pickStoreId;
    }

    public long getReturnStoreId() {
        return returnStoreId;
    }

    public void setReturnStoreId(long returnStoreId) {
        this.returnStoreId = returnStoreId;
    }
}
