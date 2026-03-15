package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractFeeDetailInfo;
import com.dflong.storecontract.service.ContractFeeDetailInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 合同费用明细表 RPC 服务实现类
 */
@DubboService
public class ContractFeeDetailInfoRpcServiceImpl implements ContractFeeDetailInfoRpcService {

    @Autowired
    private ContractFeeDetailInfoService contractFeeDetailInfoService;

    @Override
    public boolean addContractFeeDetail(ContractFeeDetailInfo contractFeeDetailInfo) {
        return contractFeeDetailInfoService.save(contractFeeDetailInfo);
    }

    @Override
    public boolean updateContractFeeDetail(ContractFeeDetailInfo contractFeeDetailInfo) {
        return contractFeeDetailInfoService.updateById(contractFeeDetailInfo);
    }

    @Override
    public boolean deleteContractFeeDetail(String detailId) {
        return contractFeeDetailInfoService.removeById(detailId);
    }

    @Override
    public ContractFeeDetailInfo getContractFeeDetailById(String detailId) {
        return contractFeeDetailInfoService.getById(detailId);
    }

    @Override
    public List<ContractFeeDetailInfo> getContractFeeDetailByItemId(String contractItemId) {
        return contractFeeDetailInfoService.getByContractItemId(contractItemId);
    }

    @Override
    public List<ContractFeeDetailInfo> getContractFeeDetailByContractId(String contractId) {
        return contractFeeDetailInfoService.getByContractId(contractId);
    }

    @Override
    public boolean updateContractFeeDetailStatus(String detailId, Integer status, String updateUser) {
        return contractFeeDetailInfoService.updateStatus(detailId, status, updateUser);
    }
}