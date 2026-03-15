package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractItemInfo;
import com.dflong.storecontract.service.ContractItemInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 合同项信息RPC服务实现类
 */
@DubboService
public class ContractItemInfoRpcServiceImpl implements ContractItemInfoRpcService {

    @Autowired
    private ContractItemInfoService contractItemInfoService;

    @Override
    public ContractItemInfo getByContractItemId(String contractItemId) {
        return contractItemInfoService.getByContractItemId(contractItemId);
    }

    @Override
    public List<ContractItemInfo> getByContractId(String contractId) {
        return contractItemInfoService.getByContractId(contractId);
    }

    @Override
    public ContractItemInfo getByPayNo(String payNo) {
        return contractItemInfoService.getByPayNo(payNo);
    }

    @Override
    public List<ContractItemInfo> getByType(Integer type) {
        return contractItemInfoService.getByType(type);
    }

    @Override
    public List<ContractItemInfo> getByStatus(Integer status) {
        return contractItemInfoService.getByStatus(status);
    }

    @Override
    public boolean createContractItem(ContractItemInfo contractItemInfo) {
        return contractItemInfoService.createContractItem(contractItemInfo);
    }

    @Override
    public boolean updateContractItem(ContractItemInfo contractItemInfo) {
        return contractItemInfoService.updateContractItem(contractItemInfo);
    }

    @Override
    public boolean updateStatus(String contractItemId, Integer status) {
        return contractItemInfoService.updateStatus(contractItemId, status);
    }
}