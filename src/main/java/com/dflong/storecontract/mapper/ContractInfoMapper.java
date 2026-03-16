package com.dflong.storecontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;
import com.dflong.storecontract.rest.bo.LastContractInfoBo;
import com.dflong.storecontract.entity.ContractInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合同信息Mapper接口
 */
@Mapper
public interface ContractInfoMapper extends BaseMapper<ContractInfo> {
    
    /**
     * 根据合同ID更新合同状态
     */
    int updateStatus(@Param("contractId") String contractId, @Param("status") Integer status);

    LastContractInfoBo getLastContract(@Param("userId") long userId);
}