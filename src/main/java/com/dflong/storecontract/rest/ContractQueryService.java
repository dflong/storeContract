package com.dflong.storecontract.rest;

import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.rest.bo.LastContractInfoBo;
import com.dflong.storecontract.mapper.ContractInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContractQueryService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    public int getLastContractStatus(long userId) {
        String ongoingContractKey = "2506:ongoingContract:" + userId;
        String lastContractStatus = redisTemplate.opsForValue().get(ongoingContractKey);
        if (lastContractStatus == null) {
            LastContractInfoBo lastContractInfoBo = contractInfoMapper.getLastContract(userId);
            if (lastContractInfoBo != null) {
                redisTemplate.opsForValue().set(ongoingContractKey, String.valueOf(lastContractInfoBo.getStatus()), 3600 * 24); // 缓存1小时
                return lastContractInfoBo.getStatus();
            } else {
                return -1; // 没有合同记录
            }
        }

        return Integer.parseInt(lastContractStatus);
    }


    public ContractInfo getByContractId(String contractId) {
        return contractInfoMapper.selectById(contractId);
    }
}
