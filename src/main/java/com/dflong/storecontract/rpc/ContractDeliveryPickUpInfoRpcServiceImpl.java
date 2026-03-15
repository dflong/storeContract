package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;
import com.dflong.storecontract.service.ContractDeliveryPickUpInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 车辆送取表 RPC 服务实现类
 */
@DubboService
public class ContractDeliveryPickUpInfoRpcServiceImpl implements ContractDeliveryPickUpInfoRpcService {

    @Autowired
    private ContractDeliveryPickUpInfoService contractDeliveryPickUpInfoService;

    @Override
    public boolean addContractDeliveryPickUp(ContractDeliveryPickUpInfo contractDeliveryPickUpInfo) {
        return contractDeliveryPickUpInfoService.save(contractDeliveryPickUpInfo);
    }

    @Override
    public boolean updateContractDeliveryPickUp(ContractDeliveryPickUpInfo contractDeliveryPickUpInfo) {
        return contractDeliveryPickUpInfoService.updateById(contractDeliveryPickUpInfo);
    }

    @Override
    public boolean deleteContractDeliveryPickUp(String deliveryPickUpId) {
        return contractDeliveryPickUpInfoService.removeById(deliveryPickUpId);
    }

    @Override
    public ContractDeliveryPickUpInfo getContractDeliveryPickUpById(String deliveryPickUpId) {
        return contractDeliveryPickUpInfoService.getById(deliveryPickUpId);
    }

    @Override
    public List<ContractDeliveryPickUpInfo> getContractDeliveryPickUpByContractId(String contractId) {
        return contractDeliveryPickUpInfoService.getByContractId(contractId);
    }

    @Override
    public ContractDeliveryPickUpInfo getContractDeliveryPickUpByContractIdAndType(String contractId, Integer type) {
        return contractDeliveryPickUpInfoService.getByContractIdAndType(contractId, type);
    }

    @Override
    public ContractDeliveryPickUpInfo getContractDeliveryPickUpByPayNo(String payNo) {
        return contractDeliveryPickUpInfoService.getByPayNo(payNo);
    }

    @Override
    public boolean updateContractDeliveryPickUpStatus(String deliveryPickUpId, Integer status, String updateUser) {
        return contractDeliveryPickUpInfoService.updateStatus(deliveryPickUpId, status, updateUser);
    }
}