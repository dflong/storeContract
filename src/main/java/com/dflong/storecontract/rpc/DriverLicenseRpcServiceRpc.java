package com.dflong.storecontract.rpc;

import com.dflong.storeapi.api.user.DriverLicense;
import com.dflong.storeapi.api.user.DriverLicenseRpcService;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DriverLicenseRpcServiceRpc {

    @DubboReference(check = false)
    DriverLicenseRpcService driverLicenseRpcService;

    @Autowired
    ThreadPool threadPool;

    public CompletableFuture<DriverLicense> getByUserId(Long userId) {
        return CompletableFuture.supplyAsync(() -> driverLicenseRpcService.getByUserId(userId), threadPool.userThreadPool());
    }
}
