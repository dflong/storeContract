package com.dflong.storecontract.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dflong.storeapi.api.Result;
import com.dflong.storeapi.api.StatusCode;
import com.dflong.storeapi.api.ThrowException;
import com.dflong.storecontract.config.idgenerator.IDGenerator;
import com.dflong.storecontract.entity.PayInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
public class ContractRest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ContractService contractService;

    /**
     * 根据支付订单号查询支付信息
     */
    @PostMapping("/create")
    public Result create(@RequestBody CreateContractDTO contractDTO) {

        IDGenerator idGenerator = new IDGenerator(redisTemplate);
        List<String> contractIds = idGenerator.getContractIds(LocalDateTime.now(), 100001 + "", 10);
        System.out.println(JSON.toJSONString(contractIds));

        try {
            String contractId = contractService.createContract(contractDTO);
            return Result.build(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), contractId);

        } catch (Exception e) {
            if (e.getCause() instanceof ThrowException) {
                ThrowException throwException = (ThrowException) e.getCause();
                return Result.build(throwException.getCode(), throwException.getMessage(), null);
            }
            return Result.build(StatusCode.INTERNAL_ERROR.getCode(), e.getMessage(), null);
        }
    }

}
