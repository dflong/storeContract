package com.dflong.storecontract.service.impl;

import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.mapper.ContractInfoMapper;
import com.dflong.storecontract.service.ContractInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 合同信息服务实现类
 */
@Service
@Transactional
public class ContractInfoServiceImpl implements ContractInfoService {

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Override
    public ContractInfo getByContractId(String contractId) {
        return contractInfoMapper.selectByContractId(contractId);
    }

    @Override
    public List<ContractInfo> getAllContracts() {
        return contractInfoMapper.selectAll();
    }

    @Override
    public List<ContractInfo> getByUserId(Long userId) {
        return contractInfoMapper.selectByUserId(userId);
    }

    @Override
    public List<ContractInfo> getByStatus(Integer status) {
        return contractInfoMapper.selectByStatus(status);
    }

    @Override
    public boolean createContract(ContractInfo contractInfo) {
        // 设置默认值
        if (contractInfo.getStatus() == null) {
            contractInfo.setStatus(1); // 默认状态：备车中
        }
        if (contractInfo.getTotalAmount() == null) {
            contractInfo.setTotalAmount(java.math.BigDecimal.ZERO);
        }
        if (contractInfo.getVehicleModelId() == null) {
            contractInfo.setVehicleModelId(0L);
        }
        if (contractInfo.getVin() == null) {
            contractInfo.setVin("");
        }
        if (contractInfo.getDeliveryVehicle() == null) {
            contractInfo.setDeliveryVehicle(2); // 默认没有送车上门服务
        }
        if (contractInfo.getPickUpVehicle() == null) {
            contractInfo.setPickUpVehicle(2); // 默认没有上门取车服务
        }
        if (contractInfo.getIsDelete() == null) {
            contractInfo.setIsDelete(1); // 默认正常状态
        }
        
        Date now = new Date();
        contractInfo.setCreateTime(now);
        contractInfo.setUpdateTime(now);
        
        if (contractInfo.getCreateBy() == null) {
            contractInfo.setCreateBy("");
        }
        if (contractInfo.getUpdateBy() == null) {
            contractInfo.setUpdateBy("");
        }
        
        return contractInfoMapper.insert(contractInfo) > 0;
    }

    @Override
    public boolean updateContract(ContractInfo contractInfo) {
        ContractInfo existing = contractInfoMapper.selectByContractId(contractInfo.getContractId());
        if (existing == null) {
            return false;
        }
        
        // 保留原有值，只更新传入的值
        if (contractInfo.getUserId() != null) {
            existing.setUserId(contractInfo.getUserId());
        }
        if (contractInfo.getStatus() != null) {
            existing.setStatus(contractInfo.getStatus());
        }
        if (contractInfo.getTotalAmount() != null) {
            existing.setTotalAmount(contractInfo.getTotalAmount());
        }
        if (contractInfo.getVehicleModelId() != null) {
            existing.setVehicleModelId(contractInfo.getVehicleModelId());
        }
        if (contractInfo.getVin() != null) {
            existing.setVin(contractInfo.getVin());
        }
        if (contractInfo.getContractStartTime() != null) {
            existing.setContractStartTime(contractInfo.getContractStartTime());
        }
        if (contractInfo.getContractEndTime() != null) {
            existing.setContractEndTime(contractInfo.getContractEndTime());
        }
        if (contractInfo.getBillingStartTime() != null) {
            existing.setBillingStartTime(contractInfo.getBillingStartTime());
        }
        if (contractInfo.getBillingEndTime() != null) {
            existing.setBillingEndTime(contractInfo.getBillingEndTime());
        }
        if (contractInfo.getDeliveryVehicle() != null) {
            existing.setDeliveryVehicle(contractInfo.getDeliveryVehicle());
        }
        if (contractInfo.getPickUpVehicle() != null) {
            existing.setPickUpVehicle(contractInfo.getPickUpVehicle());
        }
        if (contractInfo.getIsDelete() != null) {
            existing.setIsDelete(contractInfo.getIsDelete());
        }
        
        existing.setUpdateTime(new Date());
        if (contractInfo.getUpdateBy() != null) {
            existing.setUpdateBy(contractInfo.getUpdateBy());
        }
        
        return contractInfoMapper.update(existing) > 0;
    }

    @Override
    public boolean deleteContract(String contractId) {
        return contractInfoMapper.deleteByContractId(contractId) > 0;
    }

    @Override
    public boolean updateStatus(String contractId, Integer status) {
        return contractInfoMapper.updateStatus(contractId, status) > 0;
    }
}