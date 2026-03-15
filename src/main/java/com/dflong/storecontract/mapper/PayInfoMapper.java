package com.dflong.storecontract.mapper;

import com.dflong.storecontract.entity.PayInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付信息Mapper接口
 */
@Mapper
public interface PayInfoMapper {
    
    /**
     * 根据支付订单号查询支付信息
     */
    PayInfo selectByPayNo(@Param("payNo") String payNo);
    
    /**
     * 根据合同ID查询支付列表
     */
    List<PayInfo> selectByContractId(@Param("contractId") String contractId);
    
    /**
     * 根据合同项ID查询支付信息
     */
    PayInfo selectByContractItemId(@Param("contractItemId") String contractItemId);
    
    /**
     * 根据支付状态查询支付列表
     */
    List<PayInfo> selectByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有支付信息
     */
    List<PayInfo> selectAll();
    
    /**
     * 插入支付信息
     */
    int insert(PayInfo payInfo);
    
    /**
     * 更新支付信息
     */
    int update(PayInfo payInfo);
    
    /**
     * 根据支付订单号删除支付信息
     */
    int deleteByPayNo(@Param("payNo") String payNo);
    
    /**
     * 根据支付订单号更新支付状态
     */
    int updateStatus(@Param("payNo") String payNo, @Param("status") Integer status);
}