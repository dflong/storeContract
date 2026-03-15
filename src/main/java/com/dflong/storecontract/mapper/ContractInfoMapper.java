package com.dflong.storecontract.mapper;

import com.dflong.storecontract.entity.ContractInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同信息Mapper接口
 */
@Mapper
public interface ContractInfoMapper {
    
    /**
     * 根据合同ID查询合同信息
     */
    ContractInfo selectByContractId(@Param("contractId") String contractId);
    
    /**
     * 查询所有合同信息
     */
    List<ContractInfo> selectAll();
    
    /**
     * 根据用户ID查询合同列表
     */
    List<ContractInfo> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据状态查询合同列表
     */
    List<ContractInfo> selectByStatus(@Param("status") Integer status);
    
    /**
     * 插入合同信息
     */
    int insert(ContractInfo contractInfo);
    
    /**
     * 更新合同信息
     */
    int update(ContractInfo contractInfo);
    
    /**
     * 根据合同ID删除合同信息
     */
    int deleteByContractId(@Param("contractId") String contractId);
    
    /**
     * 根据合同ID更新合同状态
     */
    int updateStatus(@Param("contractId") String contractId, @Param("status") Integer status);
}