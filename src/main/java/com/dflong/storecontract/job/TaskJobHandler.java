package com.dflong.storecontract.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 任务处理器
 */
@Component
public class TaskJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(TaskJobHandler.class);


    @Autowired
    RefreshContractRanking refreshContractRanking;

    /**
     *重试任务
     * 执行频率：每5s执行一次
     */
    @XxlJob("taskTypeJobHandler")
    public void taskTypeJobHandler() throws Exception {
        logger.info("2506 开始执行重试任务......");
        refreshContractRanking.refreshContractTotalAmountRanking();
        try {
            // 查询待处理的重试记录，每次最多处理100条
//            List<RedisDeleteRetry> pendingRecords = redisDeleteRetryMapper.selectPendingRecords(100);
//
//            if (pendingRecords.isEmpty()) {
//                XxlJobHelper.log("未发现待处理的Redis删除重试记录");
//                return;
//            }
//
//            XxlJobHelper.log("发现 {} 条待处理的Redis删除重试记录", pendingRecords.size());
//
//            int successCount = 0;
//            int failureCount = 0;
//
//            for (RedisDeleteRetry record : pendingRecords) {
//                try {
//
//                } catch (Exception e) {
//
//                }
//            }

//            XxlJobHelper.log("Redis删除重试任务执行完成: 成功 {} 条, 失败 {} 条, 清理成功记录 {} 条",
//                    successCount, failureCount, 0);
                    
        } catch (Exception e) {
            XxlJobHelper.log("Redis删除重试任务执行失败: " + e.getMessage());
            logger.error("Redis删除重试任务执行失败", e);
            throw e;
        }
    }

    /**
     * 计算下次重试时间（指数退避策略）
     */
    private Date calculateNextRetryTime(int retryCount) {
        long delay = (long) Math.pow(2, retryCount) * 1000; // 2^retryCount 秒
        return new Date(System.currentTimeMillis() + delay);
    }
}