package com.dflong.storecontract.rpc;

import com.dflong.storeapi.api.base.StoreInfo;
import com.dflong.storeapi.api.base.StoreInfoApi;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StoreInfoApiRpc {

    @DubboReference(check = false)
    private StoreInfoApi storeInfoApi;

    @Autowired
    ThreadPool threadPool;

    public List<StoreInfo> listByDistance(double longitude, double latitude) {
        return storeInfoApi.listByDistance(longitude, latitude);
    }

    public CompletableFuture<StoreInfo> getStoreInfoById(long storeId) {
        return CompletableFuture.supplyAsync(() -> storeInfoApi.getStoreInfoById(storeId), threadPool.baseThreadPool());
    }

    public CompletableFuture<Double> getDistance(double longitude, double latitude, double longitude2, double latitude2) {
        return CompletableFuture.supplyAsync(() -> {
            return storeInfoApi.getDistance(longitude, latitude, longitude2, latitude2);
        }, threadPool.baseThreadPool());
    }

}
