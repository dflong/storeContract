package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.act.Coupon;
import com.dflong.storeapi.api.act.CouponRpcService;
import com.dflong.storecontract.manage.ThreadPool;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CouponRpcServiceRpc {

    @DubboReference(check = false)
    CouponRpcService couponRpcService;

    @Autowired
    private ThreadPool threadPool;

    public CompletableFuture<Coupon> getById(Long couponId) {
        return CompletableFuture.supplyAsync(() -> {
            return couponRpcService.getById(couponId);
        }, threadPool.actThreadPool());
    }

    public List<Coupon> listByUserId(Long userId) {
        return couponRpcService.listByUserId(userId);
    }

    public boolean freeze(Long couponId) {
        return couponRpcService.freeze(couponId);
    }

    public boolean unfreeze(Long couponId) {
        return couponRpcService.unfreeze(couponId);
    }

    /**
     * 核销优惠券
     */
    public boolean writeOff(Long couponId) {
        return couponRpcService.writeOff(couponId);
    }
}
