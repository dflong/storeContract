package com.dflong.storecontract.service;

import com.dflong.storecontract.entity.ContractReduceDetailInfo;

import java.util.List;

/**
 * 合同减免明细信息服务接口
 */
public interface ContractReduceDetailInfoService {
    
    /**
     * 根据减免明细ID查询减免明细信息
     */
    ContractReduceDetailInfo getByReduceDetailId(String reduceDetailId);
    
    /**
     * 根据合同项ID查询减免明细列表
     */
    List<ContractReduceDetailInfo> getByContractItemId(String contractItemId);
    
    /**
     * 根据合同ID查询减免明细列表
     */
    List<ContractReduceDetailInfo> getByContractId(String contractId);
    
    /**
     * 根据类型查询减免明细列表
     */
    List<ContractReduceDetailInfo> getByType(Integer type);
    
    /**
     * 根据状态查询减免明细列表
     */
    List<ContractReduceDetailInfo> getByStatus(Integer status);
    
    /**
     * 查询所有减免明细信息
     */
    List<ContractReduceDetailInfo> getAllReduceDetails();
    
    /**
     * 创建减免明细信息
     */
    boolean createReduceDetail(ContractReduceDetailInfo contractReduceDetailInfo);
    
    /**
     * 更新减免明细信息
     */
    boolean updateReduceDetail(ContractReduceDetailInfo contractReduceDetailInfo);
    
    /**
     * 删除减免明细信息
     */
    boolean deleteReduceDetail(String reduceDetailId);
    
    /**
     * 更新减免明细状态
     */
    boolean updateStatus(String reduceDetailId, Integer status);
}