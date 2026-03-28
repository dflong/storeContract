package com.dflong.storecontract.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.manage.DateUtils;
import com.dflong.storecontract.mapper.ContractInfoMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RefreshContractRanking {

    private static final Logger logger = LoggerFactory.getLogger(RefreshContractRanking.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    String redisKey = "2026:ranking:contract_total_amount:";

    double maxRanking = 1000000;

    @XxlJob("refreshContractTotalAmountRanking")
    public void refreshContractTotalAmountRanking() {
        Date startTime = DateUtils.fromString("2026-03-01 00:00:00");
        Date endTime = DateUtils.fromString("2026-05-01 00:00:00");

        Wrapper<ContractInfo> queryWrapper = new QueryWrapper<ContractInfo>()
                .between("create_time", startTime, endTime)
                .orderByDesc("total_amount")
                .last("limit 20");
        List<ContractInfo> contractInfoList = contractInfoMapper.selectList(queryWrapper);

        if (contractInfoList == null || contractInfoList.isEmpty()) {
            return;
        }

        Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        for (ContractInfo contractInfo : contractInfoList) {
            set.add(ZSetOperations.TypedTuple.of(contractInfo.getContractId(), maxRanking - contractInfo.getTotalAmount().doubleValue()));
        }
        redisTemplate.opsForZSet().add(redisKey, set); // 先加入
        redisTemplate.opsForZSet().removeRange(redisKey, 10, -1); // 只保留前10名
        Set<Object> objects = redisTemplate.opsForZSet().range(redisKey, 5, 9);// [start, end] score第6到第10
        logger.info("排行榜前10名: " + JSON.toJSONString(objects));
    }

}
