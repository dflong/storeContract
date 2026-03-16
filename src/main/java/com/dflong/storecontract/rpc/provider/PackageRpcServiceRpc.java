package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.act.Package;
import com.dflong.storeapi.api.act.PackageRpcService;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PackageRpcServiceRpc {

    @DubboReference(check = false)
    PackageRpcService packageRpcService;

    @Autowired
    private ThreadPool threadPool;

    public CompletableFuture<List<Package>> listByVehicleModelIdAndDayRange(long vehicleModelId, int startDay, int endDay) {
        return CompletableFuture.supplyAsync(() -> packageRpcService.listByVehicleModelIdAndDayRange(vehicleModelId, startDay, endDay), threadPool.actThreadPool());
    }

    public CompletableFuture<Package> getById(long packageId) {
        return CompletableFuture.supplyAsync(() -> packageRpcService.getById(packageId), threadPool.actThreadPool());
    }
}
