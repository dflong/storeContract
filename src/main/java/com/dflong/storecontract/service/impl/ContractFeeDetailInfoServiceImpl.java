package com.dflong.storecontract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dflong.storecontract.entity.ContractFeeDetailInfo;
import com.dflong.storecontract.mapper.ContractFeeDetailInfoMapper;
import com.dflong.storecontract.service.ContractFeeDetailInfoService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同费用明细表 Service 实现类
 */
@Service
public class ContractFeeDetailInfoServiceImpl extends ServiceImpl<ContractFeeDetailInfoMapper, ContractFeeDetailInfo> 
    implements ContractFeeDetailInfoService {

    @Override
    public List<ContractFeeDetailInfo> getByContractItemId(String contractItemId) {
        QueryWrapper<ContractFeeDetailInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_item_id", contractItemId)
                   .orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    @Override
    public List<ContractFeeDetailInfo> getByContractId(String contractId) {
        QueryWrapper<ContractFeeDetailInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id", contractId)
                   .orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    @Override
    public boolean updateStatus(String detailId, Integer status, String updateUser) {
        ContractFeeDetailInfo entity = new ContractFeeDetailInfo();
        entity.setDetailId(detailId);
        entity.setStatus(status);
        entity.setUpdateUser(updateUser);
        entity.setUpdateTime(LocalDateTime.now());
        return this.updateById(entity);
    }

    @Override
    public boolean save(ContractFeeDetailInfo entity) {
        // 设置默认值
        if (entity.getType() == null) {
            entity.setType(0);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1); // 默认进行中
        }
        if (entity.getTotalAmount() == null) {
            entity.setTotalAmount(new BigDecimal("0.00"));
        }
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(LocalDateTime.now());
        }
        if (entity.getCreateUser() == null) {
            entity.setCreateUser("");
        }
        if (entity.getUpdateTime() == null) {
            entity.setUpdateTime(LocalDateTime.now());
        }
        if (entity.getUpdateUser() == null) {
            entity.setUpdateUser("");
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(ContractFeeDetailInfo entity) {
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getUpdateUser() == null) {
            entity.setUpdateUser("");
        }
        return super.updateById(entity);
    }
}