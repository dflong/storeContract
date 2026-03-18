package com.dflong.storecontract.rpc;

import com.dflong.storeapi.api.goods.VehicleModelPrice;
import com.dflong.storeapi.api.goods.VehicleModelPriceApi;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class VehicleModelPriceApiRpc {

    @DubboReference(check = false)
    VehicleModelPriceApi vehicleModelPriceApi;

    @Autowired
    ThreadPool threadPool;

    public CompletableFuture<List<VehicleModelPrice>> listCalendarPrice(long vehicleModelId, int startDay, int endDay) {
        return CompletableFuture.supplyAsync(() -> {
           return vehicleModelPriceApi.listCalendarPrice(vehicleModelId, startDay, endDay);
        }, threadPool.goodsThreadPool());
    }
}
