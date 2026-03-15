package com.dflong.storecontract.service.impl;

import com.dflong.storecontract.entity.ContractReduceDetailInfo;
import com.dflong.storecontract.mapper.ContractReduceDetailInfoMapper;
import com.dflong.storecontract.service.ContractReduceDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 合同减免明细信息服务实现类
 */
@Service
@Transactional
public class ContractReduceDetailInfoServiceImpl implements ContractReduceDetailInfoService {

    @Autowired
    private ContractReduceDetailInfoMapper contractReduceDetailInfoMapper;

    @Override
    public ContractReduceDetailInfo getByReduceDetailId(String reduceDetailId) {
        return contractReduceDetailInfoMapper.selectByReduceDetailId(reduceDetailId);
    }

    @Override
    public List<ContractReduceDetailInfo> getByContractItemId(String contractItemId) {
        return contractReduceDetailInfoMapper.selectByContractItemId(contractItemId);
    }

    @Override
    public List<ContractReduceDetailInfo> getByContractId(String contractId) {
        return contractReduceDetailInfoMapper.selectByContractId(contractId);
    }

    @Override
    public List<ContractReduceDetailInfo> getByType(Integer type) {
        return contractReduceDetailInfoMapper.selectByType(type);
    }

    @Override
    public List<ContractReduceDetailInfo> getByStatus(Integer status) {
        return contractReduceDetailInfoMapper.selectByStatus(status);
    }

    @Override
    public List<ContractReduceDetailInfo> getAllReduceDetails() {
        return contractReduceDetailInfoMapper.selectAll();
    }

    @Override
    public boolean createReduceDetail(ContractReduceDetailInfo contractReduceDetailInfo) {
        // 设置默认值
        if (contractReduceDetailInfo.getType() == null) {
            contractReduceDetailInfo.setType(1); // 默认类型：优惠券
        }
        if (contractReduceDetailInfo.getStatus() == null) {
            contractReduceDetailInfo.setStatus(1); // 默认状态：进行中
        }
        if (contractReduceDetailInfo.getTotalAmount() == null) {
            contractReduceDetailInfo.setTotalAmount(java.math.BigDecimal.ZERO);
        }
        if (contractReduceDetailInfo.getContractItemId() == null) {
            contractReduceDetailInfo.setContractItemId("");
        }
        if (contractReduceDetailInfo.getContractId() == null) {
            contractReduceDetailInfo.setContractId("");
        }
        
        Date now = new Date();
        contractReduceDetailInfo.setCreateTime(now);
        contractReduceDetailInfo.setUpdateTime(now);
        
        if (contractReduceDetailInfo.getCreateBy() == null) {
            contractReduceDetailInfo.setCreateBy("");
        }
        if (contractReduceDetailInfo.getUpdateBy() == null) {
            contractReduceDetailInfo.setUpdateBy("");
        }
        
        return contractReduceDetailInfoMapper.insert(contractReduceDetailInfo) > 0;
    }

    @Override
    public boolean updateReduceDetail(ContractReduceDetailInfo contractReduceDetailInfo) {
        ContractReduceDetailInfo existing = contractReduceDetailInfoMapper.selectByReduceDetailId(contractReduceDetailInfo.getReduceDetailId());
        if (existing == null) {
            return false;
        }
        
        // 保留原有值，只更新传入的值
        if (contractReduceDetailInfo.getContractItemId() != null) {
            existing.setContractItemId(contractReduceDetailInfo.getContractItemId());
        }
        if (contractReduceDetailInfo.getContractId() != null) {
            existing.setContractId(contractReduceDetailInfo.getContractId());
        }
        if (contractReduceDetailInfo.getType() != null) {
            existing.setType(contractReduceDetailInfo.getType());
        }
        if (contractReduceDetailInfo.getStatus() != null) {
            existing.setStatus(contractReduceDetailInfo.getStatus());
        }
        if (contractReduceDetailInfo.getTotalAmount() != null) {
            existing.setTotalAmount(contractReduceDetailInfo.getTotalAmount());
        }
        
        existing.setUpdateTime(new Date());
        if (contractReduceDetailInfo.getUpdateBy() != null) {
            existing.setUpdateBy(contractReduceDetailInfo.getUpdateBy());
        }
        
        return contractReduceDetailInfoMapper.update(existing) > 0;
    }

    @Override
    public boolean deleteReduceDetail(String reduceDetailId) {
        return contractReduceDetailInfoMapper.deleteByReduceDetailId(reduceDetailId) > 0;
    }

    @Override
    public boolean updateStatus(String reduceDetailId, Integer status) {
        return contractReduceDetailInfoMapper.updateStatus(reduceDetailId, status) > 0;
    }
}