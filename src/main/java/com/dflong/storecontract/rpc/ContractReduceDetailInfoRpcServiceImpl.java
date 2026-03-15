package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractReduceDetailInfo;
import com.dflong.storecontract.service.ContractReduceDetailInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 合同减免明细信息RPC服务实现类
 */
@DubboService
public class ContractReduceDetailInfoRpcServiceImpl implements ContractReduceDetailInfoRpcService {

    @Autowired
    private ContractReduceDetailInfoService contractReduceDetailInfoService;

    @Override
    public ContractReduceDetailInfo getByReduceDetailId(String reduceDetailId) {
        return contractReduceDetailInfoService.getByReduceDetailId(reduceDetailId);
    }

    @Override
    public List<ContractReduceDetailInfo> getByContractItemId(String contractItemId) {
        return contractReduceDetailInfoService.getByContractItemId(contractItemId);
    }

    @Override
    public List<ContractReduceDetailInfo> getByContractId(String contractId) {
        return contractReduceDetailInfoService.getByContractId(contractId);
    }

    @Override
    public List<ContractReduceDetailInfo> getByType(Integer type) {
        return contractReduceDetailInfoService.getByType(type);
    }

    @Override
    public List<ContractReduceDetailInfo> getByStatus(Integer status) {
        return contractReduceDetailInfoService.getByStatus(status);
    }

    @Override
    public boolean createReduceDetail(ContractReduceDetailInfo contractReduceDetailInfo) {
        return contractReduceDetailInfoService.createReduceDetail(contractReduceDetailInfo);
    }

    @Override
    public boolean updateReduceDetail(ContractReduceDetailInfo contractReduceDetailInfo) {
        return contractReduceDetailInfoService.updateReduceDetail(contractReduceDetailInfo);
    }

    @Override
    public boolean updateStatus(String reduceDetailId, Integer status) {
        return contractReduceDetailInfoService.updateStatus(reduceDetailId, status);
    }
}