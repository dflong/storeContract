package com.dflong.storecontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dflong.storecontract.entity.ContractItemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同项信息Mapper接口
 */
@Mapper
public interface ContractItemInfoMapper extends BaseMapper< ContractItemInfo> {
    
    /**
     * 根据合同ID查询合同项列表
     */
    List<ContractItemInfo> selectByContractId(@Param("contractId") String contractId);
    
    /**
     * 根据支付订单号查询合同项信息
     */
    ContractItemInfo selectByPayNo(@Param("payNo") String payNo);
    
    /**
     * 根据合同项ID更新合同项状态
     */
    int updateStatus(@Param("contractItemId") String contractItemId, @Param("status") Integer status);
}