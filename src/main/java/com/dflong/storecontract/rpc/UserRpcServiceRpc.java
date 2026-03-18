package com.dflong.storecontract.rpc;

import com.dflong.storeapi.api.user.User;
import com.dflong.storeapi.api.user.UserRpcService;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserRpcServiceRpc {

    @DubboReference(check = false)
    UserRpcService userRpcService;

    @Autowired
    ThreadPool threadPool;

    public CompletableFuture<User> getById(Long userId) {
        return CompletableFuture.supplyAsync(() ->
         userRpcService.getById((userId))
        , threadPool.userThreadPool());
    }
}
