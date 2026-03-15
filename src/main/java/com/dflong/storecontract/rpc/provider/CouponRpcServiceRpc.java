package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.act.Coupon;
import com.dflong.storeapi.api.act.CouponRpcService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CouponRpcServiceRpc {

    @DubboReference(check = false)
    CouponRpcService couponRpcService;

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
