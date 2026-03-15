package com.dflong.storecontract.service.impl;

import com.dflong.storecontract.entity.ContractItemInfo;
import com.dflong.storecontract.mapper.ContractItemInfoMapper;
import com.dflong.storecontract.service.ContractItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 合同项信息服务实现类
 */
@Service
@Transactional
public class ContractItemInfoServiceImpl implements ContractItemInfoService {

    @Autowired
    private ContractItemInfoMapper contractItemInfoMapper;

    @Override
    public ContractItemInfo getByContractItemId(String contractItemId) {
        return contractItemInfoMapper.selectByContractItemId(contractItemId);
    }

    @Override
    public List<ContractItemInfo> getByContractId(String contractId) {
        return contractItemInfoMapper.selectByContractId(contractId);
    }

    @Override
    public ContractItemInfo getByPayNo(String payNo) {
        return contractItemInfoMapper.selectByPayNo(payNo);
    }

    @Override
    public List<ContractItemInfo> getByType(Integer type) {
        return contractItemInfoMapper.selectByType(type);
    }

    @Override
    public List<ContractItemInfo> getByStatus(Integer status) {
        return contractItemInfoMapper.selectByStatus(status);
    }

    @Override
    public List<ContractItemInfo> getAllContractItems() {
        return contractItemInfoMapper.selectAll();
    }

    @Override
    public boolean createContractItem(ContractItemInfo contractItemInfo) {
        // 设置默认值
        if (contractItemInfo.getType() == null) {
            contractItemInfo.setType(1); // 默认类型：下单
        }
        if (contractItemInfo.getStatus() == null) {
            contractItemInfo.setStatus(1); // 默认状态：进行中
        }
        if (contractItemInfo.getTotalAmount() == null) {
            contractItemInfo.setTotalAmount(java.math.BigDecimal.ZERO);
        }
        if (contractItemInfo.getContractId() == null) {
            contractItemInfo.setContractId("");
        }
        if (contractItemInfo.getPayNo() == null) {
            contractItemInfo.setPayNo("");
        }
        
        Date now = new Date();
        contractItemInfo.setCreateTime(now);
        contractItemInfo.setUpdateTime(now);
        
        if (contractItemInfo.getCreateBy() == null) {
            contractItemInfo.setCreateBy("");
        }
        if (contractItemInfo.getUpdateBy() == null) {
            contractItemInfo.setUpdateBy("");
        }
        
        return contractItemInfoMapper.insert(contractItemInfo) > 0;
    }

    @Override
    public boolean updateContractItem(ContractItemInfo contractItemInfo) {
        ContractItemInfo existing = contractItemInfoMapper.selectByContractItemId(contractItemInfo.getContractItemId());
        if (existing == null) {
            return false;
        }
        
        // 保留原有值，只更新传入的值
        if (contractItemInfo.getContractId() != null) {
            existing.setContractId(contractItemInfo.getContractId());
        }
        if (contractItemInfo.getType() != null) {
            existing.setType(contractItemInfo.getType());
        }
        if (contractItemInfo.getPayNo() != null) {
            existing.setPayNo(contractItemInfo.getPayNo());
        }
        if (contractItemInfo.getStatus() != null) {
            existing.setStatus(contractItemInfo.getStatus());
        }
        if (contractItemInfo.getTotalAmount() != null) {
            existing.setTotalAmount(contractItemInfo.getTotalAmount());
        }
        
        existing.setUpdateTime(new Date());
        if (contractItemInfo.getUpdateBy() != null) {
            existing.setUpdateBy(contractItemInfo.getUpdateBy());
        }
        
        return contractItemInfoMapper.update(existing) > 0;
    }

    @Override
    public boolean deleteContractItem(String contractItemId) {
        return contractItemInfoMapper.deleteByContractItemId(contractItemId) > 0;
    }

    @Override
    public boolean updateStatus(String contractItemId, Integer status) {
        return contractItemInfoMapper.updateStatus(contractItemId, status) > 0;
    }
}