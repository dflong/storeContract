package com.dflong.storecontract.rpc;

import com.dflong.storeapi.api.stock.VehicleStockApi;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class VehicleStockApiRpc {

    @DubboReference(check = false)
    private VehicleStockApi vehicleStockApi;

    @Autowired
    private ThreadPool threadPool;

    // day格式 2026050115

    public CompletableFuture<Boolean> checkStock(long vehicleModelId, int startDay, int endDay, int stockNum) {
        return CompletableFuture.supplyAsync(() -> {
            return vehicleStockApi.checkStock(vehicleModelId, startDay, endDay, stockNum);
        }, threadPool.stockThreadPool());
    }

    public CompletableFuture<Boolean> deductStock(long vehicleModelId, int startDay, int endDay, int stockNum) {
        return CompletableFuture.supplyAsync(() -> {
            return vehicleStockApi.deductStock(vehicleModelId, startDay, endDay, stockNum);
        }, threadPool.stockThreadPool());
    }

    public CompletableFuture<Boolean> releaseStock(long vehicleModelId, int startDay, int endDay, int stockNum) {
        return CompletableFuture.supplyAsync(() -> {
            return vehicleStockApi.releaseStock(vehicleModelId, startDay, endDay, stockNum);
        }, threadPool.stockThreadPool());
    }
}
