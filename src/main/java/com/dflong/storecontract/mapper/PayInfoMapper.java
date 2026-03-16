package com.dflong.storecontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dflong.storecontract.entity.PayInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付信息Mapper接口
 */
@Mapper
public interface PayInfoMapper extends BaseMapper<PayInfo> {
    
    /**
     * 根据支付订单号更新支付状态
     */
    int updateStatus(@Param("payNo") String payNo, @Param("status") Integer status);
}