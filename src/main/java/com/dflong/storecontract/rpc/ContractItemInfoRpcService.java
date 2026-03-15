package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractItemInfo;

import java.util.List;

/**
 * 合同项信息RPC服务接口
 */
public interface ContractItemInfoRpcService {
    
    /**
     * 根据合同项ID查询合同项信息
     */
    ContractItemInfo getByContractItemId(String contractItemId);
    
    /**
     * 根据合同ID查询合同项列表
     */
    List<ContractItemInfo> getByContractId(String contractId);
    
    /**
     * 根据支付订单号查询合同项信息
     */
    ContractItemInfo getByPayNo(String payNo);
    
    /**
     * 根据类型查询合同项列表
     */
    List<ContractItemInfo> getByType(Integer type);
    
    /**
     * 根据状态查询合同项列表
     */
    List<ContractItemInfo> getByStatus(Integer status);
    
    /**
     * 创建合同项信息
     */
    boolean createContractItem(ContractItemInfo contractItemInfo);
    
    /**
     * 更新合同项信息
     */
    boolean updateContractItem(ContractItemInfo contractItemInfo);
    
    /**
     * 更新合同项状态
     */
    boolean updateStatus(String contractItemId, Integer status);
}