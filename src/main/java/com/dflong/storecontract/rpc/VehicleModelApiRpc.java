package com.dflong.storecontract.rpc;

import com.dflong.storeapi.api.goods.VehicleModel;
import com.dflong.storeapi.api.goods.VehicleModelApi;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class VehicleModelApiRpc {

    @DubboReference(check = false)
    VehicleModelApi vehicleModelApi;

    @Autowired
    ThreadPool threadPool;

    public CompletableFuture<VehicleModel> getById(long vehicleModelId) {
        return CompletableFuture.supplyAsync(() -> {
            return vehicleModelApi.getById(vehicleModelId);
        }, threadPool.goodsThreadPool());
    }
}
