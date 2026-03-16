package com.dflong.storecontract.rest;

import com.dflong.storeapi.api.Result;
import com.dflong.storeapi.api.StatusCode;
import com.dflong.storeapi.api.ThrowException;
import com.dflong.storecontract.rest.dto.CreateContractDTO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/contract")
public class ContractRest {

    @Autowired
    private ContractService contractService;

    @Autowired
    private RedissonClient redissonClient;

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

}
