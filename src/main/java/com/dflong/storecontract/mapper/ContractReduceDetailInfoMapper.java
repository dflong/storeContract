package com.dflong.storecontract.mapper;

import com.dflong.storecontract.entity.ContractReduceDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同减免明细信息Mapper接口
 */
@Mapper
public interface ContractReduceDetailInfoMapper {
    
    /**
     * 根据减免明细ID查询减免明细信息
     */
    ContractReduceDetailInfo selectByReduceDetailId(@Param("reduceDetailId") String reduceDetailId);
    
    /**
     * 根据合同项ID查询减免明细列表
     */
    List<ContractReduceDetailInfo> selectByContractItemId(@Param("contractItemId") String contractItemId);
    
    /**
     * 根据合同ID查询减免明细列表
     */
    List<ContractReduceDetailInfo> selectByContractId(@Param("contractId") String contractId);
    
    /**
     * 根据类型查询减免明细列表
     */
    List<ContractReduceDetailInfo> selectByType(@Param("type") Integer type);
    
    /**
     * 根据状态查询减免明细列表
     */
    List<ContractReduceDetailInfo> selectByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有减免明细信息
     */
    List<ContractReduceDetailInfo> selectAll();
    
    /**
     * 插入减免明细信息
     */
    int insert(ContractReduceDetailInfo contractReduceDetailInfo);
    
    /**
     * 更新减免明细信息
     */
    int update(ContractReduceDetailInfo contractReduceDetailInfo);
    
    /**
     * 根据减免明细ID删除减免明细信息
     */
    int deleteByReduceDetailId(@Param("reduceDetailId") String reduceDetailId);
    
    /**
     * 根据减免明细ID更新减免明细状态
     */
    int updateStatus(@Param("reduceDetailId") String reduceDetailId, @Param("status") Integer status);
}