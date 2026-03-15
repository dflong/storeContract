package com.dflong.storecontract.service;

import com.dflong.storecontract.entity.ContractInfo;

import java.util.List;

/**
 * 合同信息服务接口
 */
public interface ContractInfoService {
    
    /**
     * 根据合同ID查询合同信息
     */
    ContractInfo getByContractId(String contractId);
    
    /**
     * 查询所有合同信息
     */
    List<ContractInfo> getAllContracts();
    
    /**
     * 根据用户ID查询合同列表
     */
    List<ContractInfo> getByUserId(Long userId);
    
    /**
     * 根据状态查询合同列表
     */
    List<ContractInfo> getByStatus(Integer status);
    
    /**
     * 创建合同
     */
    boolean createContract(ContractInfo contractInfo);
    
    /**
     * 更新合同信息
     */
    boolean updateContract(ContractInfo contractInfo);
    
    /**
     * 删除合同
     */
    boolean deleteContract(String contractId);
    
    /**
     * 更新合同状态
     */
    boolean updateStatus(String contractId, Integer status);
}