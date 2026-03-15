package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.PayInfo;

import java.util.List;

/**
 * 支付信息RPC服务接口
 */
public interface PayInfoRpcService {
    
    /**
     * 根据支付订单号查询支付信息
     */
    PayInfo getByPayNo(String payNo);
    
    /**
     * 根据合同ID查询支付列表
     */
    List<PayInfo> getByContractId(String contractId);
    
    /**
     * 根据合同项ID查询支付信息
     */
    PayInfo getByContractItemId(String contractItemId);
    
    /**
     * 根据支付状态查询支付列表
     */
    List<PayInfo> getByStatus(Integer status);
    
    /**
     * 创建支付信息
     */
    boolean createPayInfo(PayInfo payInfo);
    
    /**
     * 更新支付信息
     */
    boolean updatePayInfo(PayInfo payInfo);
    
    /**
     * 更新支付状态
     */
    boolean updateStatus(String payNo, Integer status);
}