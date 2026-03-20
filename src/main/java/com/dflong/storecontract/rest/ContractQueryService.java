package com.dflong.storecontract.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.entity.ContractItemInfo;
import com.dflong.storecontract.mapper.ContractItemInfoMapper;
import com.dflong.storecontract.rest.bo.ContractInfoBo;
import com.dflong.storecontract.rest.bo.LastContractInfoBo;
import com.dflong.storecontract.mapper.ContractInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractQueryService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    ContractItemInfoMapper contractItemInfoMapper;

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

    public List<ContractInfoBo> listContracts() throws InterruptedException {
        List<ContractInfoBo> contractInfoBoList = new ArrayList<>();
        List<ContractInfo> contractInfoList = contractInfoMapper.selectList(new QueryWrapper<ContractInfo>().orderByDesc("contract_id").last("limit 100"));
        for (ContractInfo contractInfo : contractInfoList) {
            List<ContractItemInfo> contractItemInfos = contractItemInfoMapper.selectByContractId(contractInfo.getContractId());
            ContractInfoBo contractInfoBo = new ContractInfoBo();
            BeanUtils.copyProperties(contractInfo, contractInfoBo);
            contractInfoBo.setContractItemInfos(contractItemInfos);
            contractInfoBoList.add(contractInfoBo);
        }
        Thread.sleep(1000);
        cache.addAll(contractInfoBoList);
        return contractInfoBoList;
    }

    List<ContractInfoBo> cache = new ArrayList<>();
}
