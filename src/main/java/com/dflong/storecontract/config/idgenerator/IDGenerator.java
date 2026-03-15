package com.dflong.storecontract.config.idgenerator;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IDGenerator {

    public IDGenerator(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getContractId(LocalDateTime daytime, String userId) {
        return getTableId(CONTRACT_PREFIX, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getContractIds(LocalDateTime daytime, String userId, int idNum) {
        return getTableId(CONTRACT_PREFIX, LocalDateTime.now(), userId, idNum);
    }

    public String getContractItemId(LocalDateTime daytime, String userId) {
        return getTableId(CONTRACT_ITEM_PREFIX, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getContractItemIds(LocalDateTime daytime, String userId, int idNum) {
        return getTableId(CONTRACT_ITEM_PREFIX, LocalDateTime.now(), userId, idNum);
    }

    public String getDeliveryPickUpId(LocalDateTime daytime, String userId) {
        return getTableId(CONTRACT_D_P_PREFIX, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getDeliveryPickUpIds(LocalDateTime daytime, String userId, int idNum) {
        return getTableId(CONTRACT_D_P_PREFIX, LocalDateTime.now(), userId, idNum);
    }

    public String getDetailId(LocalDateTime daytime, String userId) {
        return getTableId(CONTRACT_FEE_DETAIL_PREFIX, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getDetailIds(LocalDateTime daytime, String userId, int idNum) {
        return getTableId(CONTRACT_FEE_DETAIL_PREFIX, LocalDateTime.now(), userId, idNum);
    }

    public String getReduceDetailId(LocalDateTime daytime, String userId) {
        return getTableId(CONTRACT_REDUCE_DETAIL_PREFIX, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getReduceDetailIds(LocalDateTime daytime, String userId, int idNum) {
        return getTableId(CONTRACT_REDUCE_DETAIL_PREFIX, LocalDateTime.now(), userId, idNum);
    }

    public String getPayNo(LocalDateTime daytime, String userId) {
        return getTableId(CONTRACT_PAY_PREFIX, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getPayNos(LocalDateTime daytime, String userId, int idNum) {
        return getTableId(CONTRACT_PAY_PREFIX, LocalDateTime.now(), userId, idNum);
    }

    public String getIdByPrefix(String prefix, LocalDateTime daytime, String userId) {
        return getTableId(prefix, LocalDateTime.now(), userId, 1).get(0);
    }

    public List<String> getIdByPrefixList(String prefix, LocalDateTime daytime, String userId, int idNum) {
        return getTableId(prefix, LocalDateTime.now(), userId, idNum);
    }

    private List<String> getTableId(String prefix, LocalDateTime daytime, String userId, int idNum) {
        List<String> ids = new ArrayList<>(idNum);

        StringBuilder sb = new StringBuilder();

        // MD 2026040115 00000025 123456
        sb.append(prefix); // MD

        String dayTimeStr = daytime.format(HOUR_FMT);
        sb.append(dayTimeStr); // 2026040115

        sb.append("%08d"); // 00000025

        String userStr = userId.substring(userId.length() - 6);
        sb.append(userStr); // 123456

        String redisKey = "seq:MD:" + prefix + ":" + dayTimeStr;

        long seqNo = redisTemplate.opsForValue().increment(redisKey, idNum);
        redisTemplate.expire(redisKey, 2, TimeUnit.HOURS);
        long startSeqNo = seqNo - idNum + 1;
        for (int i = 0; i < idNum; i ++) {
            String id = String.format(sb.toString(), startSeqNo + i); // 替换 %08d
            ids.add(id);
        }

        return ids;
    }

    RedisTemplate<String, Object> redisTemplate;

    private final String CONTRACT_PREFIX = "MC";

    private final String CONTRACT_ITEM_PREFIX = "CI";

    private final String CONTRACT_D_P_PREFIX = "DP";

    private final String CONTRACT_FEE_DETAIL_PREFIX = "FD";

    private final String CONTRACT_REDUCE_DETAIL_PREFIX = "RD";

    private final String CONTRACT_PAY_PREFIX = "CP";

    private static final DateTimeFormatter HOUR_FMT = DateTimeFormatter.ofPattern("yyyyMMddHH");
}

