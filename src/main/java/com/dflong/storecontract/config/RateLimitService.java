package com.dflong.storecontract.config;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {

    @Autowired
    @Lazy
    private RedissonClient redissonClient;

    /**
     * 分布式限流
     * @param key 限流器key，如："2506:createOrder:${userId}"
     * @param rate 每秒令牌数
     * @param rateInterval 时间间隔  每 rateInterval 秒 产生 rate 个令牌数
     * @return 是否获取成功
     */
    public boolean tryAcquire(String key, int rate, int rateInterval) {
        RRateLimiter limiter = redissonClient.getRateLimiter(key);
        // 懒初始化，如果已设置会忽略
        limiter.trySetRate(RateType.OVERALL, rate, rateInterval, RateIntervalUnit.SECONDS);
        // lua 记录了上次更新时间，每次获取时判断两次时间内要生成多少个令牌数
        return limiter.tryAcquire();
    }
}
