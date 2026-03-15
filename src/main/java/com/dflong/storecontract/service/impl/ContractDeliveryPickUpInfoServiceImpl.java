package com.dflong.storecontract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;
import com.dflong.storecontract.mapper.ContractDeliveryPickUpInfoMapper;
import com.dflong.storecontract.service.ContractDeliveryPickUpInfoService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆送取表 Service 实现类
 */
@Service
public class ContractDeliveryPickUpInfoServiceImpl extends ServiceImpl<ContractDeliveryPickUpInfoMapper, ContractDeliveryPickUpInfo> 
    implements ContractDeliveryPickUpInfoService {

    @Override
    public List<ContractDeliveryPickUpInfo> getByContractId(String contractId) {
        QueryWrapper<ContractDeliveryPickUpInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id", contractId)
                   .orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    @Override
    public ContractDeliveryPickUpInfo getByContractIdAndType(String contractId, Integer type) {
        QueryWrapper<ContractDeliveryPickUpInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id", contractId)
                   .eq("type", type)
                   .orderByDesc("create_time")
                   .last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public ContractDeliveryPickUpInfo getByPayNo(String payNo) {
        QueryWrapper<ContractDeliveryPickUpInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pay_no", payNo)
                   .orderByDesc("create_time")
                   .last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateStatus(String deliveryPickUpId, Integer status, String updateUser) {
        ContractDeliveryPickUpInfo entity = new ContractDeliveryPickUpInfo();
        entity.setDeliveryPickUpId(deliveryPickUpId);
        entity.setStatus(status);
        entity.setUpdateUser(updateUser);
        entity.setUpdateTime(LocalDateTime.now());
        return this.updateById(entity);
    }

    @Override
    public boolean save(ContractDeliveryPickUpInfo entity) {
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
        if (entity.getPayNo() == null) {
            entity.setPayNo("");
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
    public boolean updateById(ContractDeliveryPickUpInfo entity) {
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getUpdateUser() == null) {
            entity.setUpdateUser("");
        }
        return super.updateById(entity);
    }
}