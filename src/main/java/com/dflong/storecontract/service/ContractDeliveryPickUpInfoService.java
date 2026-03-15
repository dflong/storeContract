package com.dflong.storecontract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;

/**
 * 车辆送取表 Service 接口
 */
public interface ContractDeliveryPickUpInfoService extends IService<ContractDeliveryPickUpInfo> {
    
    /**
     * 根据合同ID查询送取信息
     * @param contractId 合同ID
     * @return 送取信息列表
     */
    java.util.List<ContractDeliveryPickUpInfo> getByContractId(String contractId);
    
    /**
     * 根据合同ID和类型查询送取信息
     * @param contractId 合同ID
     * @param type 类型 1：送车上门 2：上门取车
     * @return 送取信息
     */
    ContractDeliveryPickUpInfo getByContractIdAndType(String contractId, Integer type);
    
    /**
     * 根据支付订单号查询送取信息
     * @param payNo 支付订单号
     * @return 送取信息
     */
    ContractDeliveryPickUpInfo getByPayNo(String payNo);
    
    /**
     * 更新送取信息状态
     * @param deliveryPickUpId 送取ID
     * @param status 状态
     * @param updateUser 更新人
     * @return 是否成功
     */
    boolean updateStatus(String deliveryPickUpId, Integer status, String updateUser);
}