package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.service.ContractInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 合同信息RPC服务实现类
 */
@DubboService
public class ContractInfoRpcServiceImpl implements ContractInfoRpcService {

    @Autowired
    private ContractInfoService contractInfoService;

    @Override
    public ContractInfo getByContractId(String contractId) {
        return contractInfoService.getByContractId(contractId);
    }

    @Override
    public List<ContractInfo> getByUserId(Long userId) {
        return contractInfoService.getByUserId(userId);
    }

    @Override
    public List<ContractInfo> getByStatus(Integer status) {
        return contractInfoService.getByStatus(status);
    }

    @Override
    public boolean createContract(ContractInfo contractInfo) {
        return contractInfoService.createContract(contractInfo);
    }

    @Override
    public boolean updateContract(ContractInfo contractInfo) {
        return contractInfoService.updateContract(contractInfo);
    }

    @Override
    public boolean updateStatus(String contractId, Integer status) {
        return contractInfoService.updateStatus(contractId, status);
    }
}