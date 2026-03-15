package com.dflong.storecontract.service.impl;

import com.dflong.storecontract.entity.PayInfo;
import com.dflong.storecontract.mapper.PayInfoMapper;
import com.dflong.storecontract.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 支付信息服务实现类
 */
@Service
@Transactional
public class PayInfoServiceImpl implements PayInfoService {

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Override
    public PayInfo getByPayNo(String payNo) {
        return payInfoMapper.selectByPayNo(payNo);
    }

    @Override
    public List<PayInfo> getByContractId(String contractId) {
        return payInfoMapper.selectByContractId(contractId);
    }

    @Override
    public PayInfo getByContractItemId(String contractItemId) {
        return payInfoMapper.selectByContractItemId(contractItemId);
    }

    @Override
    public List<PayInfo> getByStatus(Integer status) {
        return payInfoMapper.selectByStatus(status);
    }

    @Override
    public List<PayInfo> getAllPayInfo() {
        return payInfoMapper.selectAll();
    }

    @Override
    public boolean createPayInfo(PayInfo payInfo) {
        // 设置默认值
        if (payInfo.getStatus() == null) {
            payInfo.setStatus(1); // 默认状态：待支付
        }
        if (payInfo.getTotalAmount() == null) {
            payInfo.setTotalAmount(java.math.BigDecimal.ZERO);
        }
        if (payInfo.getContractId() == null) {
            payInfo.setContractId("");
        }
        if (payInfo.getContractItemId() == null) {
            payInfo.setContractItemId("");
        }
        
        Date now = new Date();
        payInfo.setCreateTime(now);
        payInfo.setUpdateTime(now);
        
        if (payInfo.getCreateBy() == null) {
            payInfo.setCreateBy("");
        }
        if (payInfo.getUpdateBy() == null) {
            payInfo.setUpdateBy("");
        }
        
        return payInfoMapper.insert(payInfo) > 0;
    }

    @Override
    public boolean updatePayInfo(PayInfo payInfo) {
        PayInfo existing = payInfoMapper.selectByPayNo(payInfo.getPayNo());
        if (existing == null) {
            return false;
        }
        
        // 保留原有值，只更新传入的值
        if (payInfo.getContractId() != null) {
            existing.setContractId(payInfo.getContractId());
        }
        if (payInfo.getContractItemId() != null) {
            existing.setContractItemId(payInfo.getContractItemId());
        }
        if (payInfo.getStatus() != null) {
            existing.setStatus(payInfo.getStatus());
        }
        if (payInfo.getTotalAmount() != null) {
            existing.setTotalAmount(payInfo.getTotalAmount());
        }
        
        existing.setUpdateTime(new Date());
        if (payInfo.getUpdateBy() != null) {
            existing.setUpdateBy(payInfo.getUpdateBy());
        }
        
        return payInfoMapper.update(existing) > 0;
    }

    @Override
    public boolean deletePayInfo(String payNo) {
        return payInfoMapper.deleteByPayNo(payNo) > 0;
    }

    @Override
    public boolean updateStatus(String payNo, Integer status) {
        return payInfoMapper.updateStatus(payNo, status) > 0;
    }
}