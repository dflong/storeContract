package com.dflong.storecontract.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dflong.storeapi.api.util.IDGenerator;
import com.dflong.storecontract.config.XxlJobConfig;
import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.entity.ContractItemInfo;
import com.dflong.storecontract.mapper.ContractItemInfoMapper;
import com.dflong.storecontract.rest.bo.ContractInfoBo;
import com.dflong.storecontract.rest.bo.LastContractInfoBo;
import com.dflong.storecontract.mapper.ContractInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContractQueryService {

    private Logger log = LoggerFactory.getLogger(XxlJobConfig.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    ContractItemInfoMapper contractItemInfoMapper;

    public int getLastContractStatus(long userId) {
        String ongoingContractKey = "2506:ongoingContract:" + userId;
        log.debug("userId {} logged in from {}", userId, ongoingContractKey);

        String lastContractStatus = (String) redisTemplate.opsForValue().get(ongoingContractKey);
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
        IDGenerator idGenerator = new IDGenerator(redisTemplate);
        List<ContractInfoBo> contractInfoBoList = new ArrayList<>();
        List<ContractInfo> contractInfoList = contractInfoMapper.selectList(new QueryWrapper<ContractInfo>().orderByDesc("contract_id").last("limit 100"));
        for (ContractInfo contractInfo : contractInfoList) {
            List<ContractItemInfo> contractItemInfos = contractItemInfoMapper.selectByContractId(contractInfo.getContractId());
            ContractInfoBo contractInfoBo = new ContractInfoBo();
            BeanUtils.copyProperties(contractInfo, contractInfoBo);
            contractInfoBo.setUpdateTime(new Date());
            contractInfoBo.setCreateTime(new Date());
            contractInfoBo.setCreateBy("admin");
            contractInfoBo.setUpdateBy("admin");
            contractInfoBo.setContractId(idGenerator.getContractId(LocalDateTime.now(), contractInfo.getUserId() + ""));
            contractInfoBo.setContractItemInfos(contractItemInfos);
            contractInfoBoList.add(contractInfoBo);
        }
//        cache.addAll(contractInfoBoList);
        return contractInfoBoList;
    }

    List<ContractInfoBo> cache = new ArrayList<>();
}

// [Full GC (Allocation Failure) [CMS[CMS-concurrent-sweep: 0.031/0.100 secs] [Times: user=0.09 sys=0.01, real=0.10 secs]
// (concurrent mode failure CMS运行期间预留的内存无法满足程序分配新对象的需要， 就会出现一次“并发失败): 174784K->174783K(174784K), 0.4596860 secs] 253440K->195545K(253440K), [Metaspace: 88839K->88839K(1140736K)], 0.4598673 secs] [Times: user=0.44 sys=0.01, real=0.46 secs]

// [GC (Allocation Failure) [ParNew: 78334K->7118K(78656K), 0.0376058 secs] 251755K->181179K(253440K), 0.0377651 secs] [Times: user=0.06 sys=0.02, real=0.04 secs]
// [GC (Allocation Failure) [ParNew (promotion failed 老年代晋升失败): 77070K->78377K(78656K), 0.0581954 secs][CMS[CMS-concurrent-mark: 0.105/0.643 secs] [Times: user=0.83 sys=0.25, real=0.64 secs]