package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.PayInfo;
import com.dflong.storecontract.service.PayInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 支付信息RPC服务实现类
 */
@DubboService
public class PayInfoRpcServiceImpl implements PayInfoRpcService {

    @Autowired
    private PayInfoService payInfoService;

    @Override
    public PayInfo getByPayNo(String payNo) {
        return payInfoService.getByPayNo(payNo);
    }

    @Override
    public List<PayInfo> getByContractId(String contractId) {
        return payInfoService.getByContractId(contractId);
    }

    @Override
    public PayInfo getByContractItemId(String contractItemId) {
        return payInfoService.getByContractItemId(contractItemId);
    }

    @Override
    public List<PayInfo> getByStatus(Integer status) {
        return payInfoService.getByStatus(status);
    }

    @Override
    public boolean createPayInfo(PayInfo payInfo) {
        return payInfoService.createPayInfo(payInfo);
    }

    @Override
    public boolean updatePayInfo(PayInfo payInfo) {
        return payInfoService.updatePayInfo(payInfo);
    }

    @Override
    public boolean updateStatus(String payNo, Integer status) {
        return payInfoService.updateStatus(payNo, status);
    }
}