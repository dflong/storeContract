package com.dflong.storecontract.config;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 使用 Redis 自增序列生成主键，并在 insert 时自动填充标注了 @TableId 的字段（若为空）
 * 生成规则：MD + yyyyMMddHH + redisSequence(8位，补0) + userId尾6位
 */
@Component
public class RedisIdMetaObjectHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisIdMetaObjectHandler.class);

    private static final DateTimeFormatter HOUR_FMT = DateTimeFormatter.ofPattern("yyyyMMddHH");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void insertFill(MetaObject metaObject) {
        Object original = metaObject.getOriginalObject();
        if (original == null) {
            return;
        }

        Field[] fields = original.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableId.class)) {
                String propName = field.getName();
                Object current = metaObject.getValue(propName);
                boolean needSet = false;
                if (current == null) {
                    needSet = true;
                } else if (current instanceof String && ((String) current).trim().isEmpty()) {
                    needSet = true;
                }

                if (needSet) {
                    try {
                        String id = generateId(original);
                        metaObject.setValue(propName, id);
                        logger.debug("Auto-filled id for {} -> {}", original.getClass().getSimpleName(), id);
                    } catch (Exception e) {
                        logger.error("Failed to auto-generate id for {}", original.getClass().getSimpleName(), e);
                    }
                }
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // no-op
    }

    private String generateId(Object entity) {
        String prefix = "MD";
        String timePart = LocalDateTime.now().format(HOUR_FMT);
        String redisKey = "seq:MD:" + timePart;

        Long seq = stringRedisTemplate.opsForValue().increment(redisKey);
        // set TTL for the key to 2 days to avoid excessive keys
        stringRedisTemplate.expire(redisKey, 2, TimeUnit.HOURS);

        if (seq == null) {
            throw new IllegalStateException("Redis did not return sequence number");
        }

        String seqPart = String.format("%08d", seq);

        // try to get userId field value (prefer numeric id). If not present, use 000000
        String userSuffix = "000000";
        try {
            Field userIdField = null;
            Class<?> cls = entity.getClass();
            // try common field names
            String[] candidates = new String[]{"userId", "createUser", "creator", "create_by", "createBy"};
            for (String name : candidates) {
                try {
                    userIdField = cls.getDeclaredField(name);
                    break;
                } catch (NoSuchFieldException ignored) {
                }
            }
            if (userIdField != null) {
                userIdField.setAccessible(true);
                Object val = userIdField.get(entity);
                if (val != null) {
                    String vs = String.valueOf(val);
                    // keep digits only
                    String digits = vs.replaceAll("\\D", "");
                    if (!digits.isEmpty()) {
                        if (digits.length() >= 6) {
                            userSuffix = digits.substring(digits.length() - 6);
                        } else {
                            userSuffix = String.format("%6s", digits).replace(' ', '0');
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Unable to read userId from entity {}: {}", entity.getClass().getSimpleName(), e.getMessage());
        }

        return prefix + timePart + seqPart + userSuffix;
    }
}

