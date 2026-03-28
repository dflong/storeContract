package com.dflong.storecontract.rest;

import com.dflong.storeapi.api.Result;
import com.dflong.storeapi.api.StatusCode;
import com.dflong.storeapi.api.ThrowException;
import com.dflong.storecontract.config.RateLimitService;
import com.dflong.storecontract.rest.bo.ContractInfoBo;
import com.dflong.storecontract.rest.dto.CreateContractDTO;
import com.google.common.util.concurrent.RateLimiter;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/contract")
public class ContractRest {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractQueryService queryService;

    @Autowired
    private RedissonClient redissonClient;

    private Logger log = LoggerFactory.getLogger(ContractRest.class);

    /**
     * 根据支付订单号查询支付信息
     */
    @PostMapping("/create")
    public Result create(@RequestBody CreateContractDTO contractDTO) {
        RLock lock = redissonClient.getLock("2506:create:" + contractDTO.getUserId());
        try {
            boolean isLocked = lock.tryLock(0, - 1, TimeUnit.SECONDS);
            if (isLocked) {
                String contractId = contractService.createContract(contractDTO);
                return Result.build(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), contractId);
            }
        } catch (Exception e) {
            if (e.getCause() instanceof ThrowException) {
                ThrowException throwException = (ThrowException) e.getCause();
                return Result.build(throwException.getCode(), throwException.getMessage(), null);
            }
            return Result.build(StatusCode.INTERNAL_ERROR.getCode(), e.getMessage(), "");
        } finally {
            // 确保锁被释放，只有持有锁的线程才能释放
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return Result.build(-2506001, "请勿重试", "");
    }

    // 限流：每秒最多处理10个创建订单请求 单机guava
    private final RateLimiter orderCreateLimiter = RateLimiter.create(60.0);

    @Autowired
    RateLimitService rateLimitService;

    @GetMapping("/list")
    public Result list() throws InterruptedException {
        // 模拟限流
        if (!orderCreateLimiter.tryAcquire()) {
            log.info("下单失败，稍后重试 限流拦截");
            return Result.build(StatusCode.TOO_MANY_REQUESTS.getCode(), "下单失败，稍后重试", "");
        }
        if (!rateLimitService.tryAcquire("2506:createOrder:list", 2, 5)) {
            log.info("下单失败，稍后重试redis 限流拦截");
            return Result.build(StatusCode.TOO_MANY_REQUESTS.getCode(), "下单失败，稍后重试redis 限流拦截", "");
        }
        List<ContractInfoBo> contractInfoList = queryService.listContracts();
        return Result.build(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), contractInfoList);
    }

}
