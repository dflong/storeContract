package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dflong.storeapi.api.ConstantStatus;
import com.dflong.storeapi.api.PickUpTypeStatus;
import com.dflong.storeapi.api.billing.CreateContractBillingBo;
import com.dflong.storeapi.api.contract.StoreInfBo;
import com.dflong.storecontract.manage.DateUtils;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车辆送取表
 */
@Data
@TableName("contract_delivery_pick_up_info")
public class ContractDeliveryPickUpInfo {
    
    /**
     * 车辆送取id
     */
    @TableId(value = "delivery_pick_up_id", type = IdType.INPUT)
    private String deliveryPickUpId;
    
    /**
     * 合同id
     */
    private String contractId;
    
    /**
     * 类型 1：送车上门 2：上门取车
     */
    private Integer type;
    
    /**
     * 支付订单号
     */
    private String payNo;
    
    /**
     * 状态 1:进行中 2：已取消
     */
    private Integer status;
    
    /**
     * 金额
     */
    private BigDecimal totalAmount;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 距离（米）
     */
    private Double distance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 更新人
     */
    private String updateUser;

    public static ContractDeliveryPickUpInfo build(String deliveryPickUpId, String contractId, StoreInfBo storeInfBo, String payNo, long userId, LocalDateTime now, int type) {
        ContractDeliveryPickUpInfo info = new ContractDeliveryPickUpInfo();

        info.setDeliveryPickUpId(deliveryPickUpId);
        info.setContractId(contractId);
        info.setType(type);
        info.setPayNo(payNo);
        info.setStatus(ConstantStatus.SUCCESS.getCode());

        if (type == PickUpTypeStatus.DELIVERY.getCode()) {
            info.setTotalAmount(storeInfBo.getPickUpPrice());
            info.setLongitude(storeInfBo.getDeliveryLongitude());
            info.setLatitude(storeInfBo.getDeliveryLatitude());
            info.setDistance(storeInfBo.getPickUpDistance());
        } else {
            info.setTotalAmount(storeInfBo.getReturnPrice());
            info.setLongitude(storeInfBo.getPickUpLongitude());
            info.setLatitude(storeInfBo.getPickUpLatitude());
            info.setDistance(storeInfBo.getReturnDistance());
        }

        info.setCreateUser(userId + "");
        info.setCreateTime(now);
        info.setUpdateUser(userId + "");
        info.setUpdateTime(now);
        return info;
    }
}