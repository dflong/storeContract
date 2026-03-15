package com.dflong.storecontract.mapper;

import com.dflong.storecontract.entity.ContractItemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同项信息Mapper接口
 */
@Mapper
public interface ContractItemInfoMapper {
    
    /**
     * 根据合同项ID查询合同项信息
     */
    ContractItemInfo selectByContractItemId(@Param("contractItemId") String contractItemId);
    
    /**
     * 根据合同ID查询合同项列表
     */
    List<ContractItemInfo> selectByContractId(@Param("contractId") String contractId);
    
    /**
     * 根据支付订单号查询合同项信息
     */
    ContractItemInfo selectByPayNo(@Param("payNo") String payNo);
    
    /**
     * 根据类型查询合同项列表
     */
    List<ContractItemInfo> selectByType(@Param("type") Integer type);
    
    /**
     * 根据状态查询合同项列表
     */
    List<ContractItemInfo> selectByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有合同项信息
     */
    List<ContractItemInfo> selectAll();
    
    /**
     * 插入合同项信息
     */
    int insert(ContractItemInfo contractItemInfo);
    
    /**
     * 更新合同项信息
     */
    int update(ContractItemInfo contractItemInfo);
    
    /**
     * 根据合同项ID删除合同项信息
     */
    int deleteByContractItemId(@Param("contractItemId") String contractItemId);
    
    /**
     * 根据合同项ID更新合同项状态
     */
    int updateStatus(@Param("contractItemId") String contractItemId, @Param("status") Integer status);
}