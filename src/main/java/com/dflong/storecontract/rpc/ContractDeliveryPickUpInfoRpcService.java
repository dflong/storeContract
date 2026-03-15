package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;
import java.util.List;

/**
 * 车辆送取表 RPC 服务接口
 */
public interface ContractDeliveryPickUpInfoRpcService {
    
    /**
     * 新增送取信息
     */
    boolean addContractDeliveryPickUp(ContractDeliveryPickUpInfo contractDeliveryPickUpInfo);
    
    /**
     * 修改送取信息
     */
    boolean updateContractDeliveryPickUp(ContractDeliveryPickUpInfo contractDeliveryPickUpInfo);
    
    /**
     * 删除送取信息
     */
    boolean deleteContractDeliveryPickUp(String deliveryPickUpId);
    
    /**
     * 根据ID查询送取信息
     */
    ContractDeliveryPickUpInfo getContractDeliveryPickUpById(String deliveryPickUpId);
    
    /**
     * 根据合同ID查询送取信息
     */
    List<ContractDeliveryPickUpInfo> getContractDeliveryPickUpByContractId(String contractId);
    
    /**
     * 根据合同ID和类型查询送取信息
     */
    ContractDeliveryPickUpInfo getContractDeliveryPickUpByContractIdAndType(String contractId, Integer type);
    
    /**
     * 根据支付订单号查询送取信息
     */
    ContractDeliveryPickUpInfo getContractDeliveryPickUpByPayNo(String payNo);
    
    /**
     * 更新送取信息状态
     */
    boolean updateContractDeliveryPickUpStatus(String deliveryPickUpId, Integer status, String updateUser);
}