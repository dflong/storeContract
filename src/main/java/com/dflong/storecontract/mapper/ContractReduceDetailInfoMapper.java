package com.dflong.storecontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dflong.storecontract.entity.ContractReduceDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同减免明细信息Mapper接口
 */
@Mapper
public interface ContractReduceDetailInfoMapper extends BaseMapper<ContractReduceDetailInfo> {
    
    /**
     * 根据合同项ID查询减免明细列表
     */
    List<ContractReduceDetailInfo> selectByContractItemId(@Param("contractItemId") String contractItemId);

}