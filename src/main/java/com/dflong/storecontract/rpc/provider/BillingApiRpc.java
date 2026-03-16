package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.billing.BillingApi;
import com.dflong.storeapi.api.billing.CreateContractBillingBo;
import com.dflong.storeapi.api.billing.CreateContractBillingDto;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BillingApiRpc {

    @DubboReference(check = false)
    BillingApi billingApi;

    @Autowired
    ThreadPool threadPool;

    public CompletableFuture<CreateContractBillingBo> orderContractAmount(CreateContractBillingDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            return billingApi.orderContractAmount(dto);
        }, threadPool.billingThreadPool());
    }
}
