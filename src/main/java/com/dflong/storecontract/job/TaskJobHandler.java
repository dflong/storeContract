package com.dflong.storecontract.job;

import com.dflong.storecontract.constant.TaskJobType;
import com.dflong.storecontract.entity.TaskJob;
import com.dflong.storecontract.mapper.TaskJobMapper;
import com.dflong.storecontract.rpc.CouponRpcServiceRpc;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 任务处理器
 */
@Component
public class TaskJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(TaskJobHandler.class);

    @Autowired
    TaskJobMapper taskJobMapper;

    @Autowired
    CouponRpcServiceRpc couponRpcServiceRpc;

    /**
     *重试任务
     * 执行频率：每5s执行一次
     */
    @XxlJob("taskTypeJobHandler")
    public void taskTypeJobHandler() throws Exception {
        logger.info("2506 开始执行重试任务......");
        try {
            List<TaskJob> taskJobs = taskJobMapper.selectPendingRecords(1, new Date());
            if (taskJobs == null || taskJobs.isEmpty()) return;
            for (TaskJob taskJob : taskJobs) {
                boolean runSuccess = false;
                if (taskJob.getTaskType() == TaskJobType.FREEZE_COUPON) {
                    runSuccess = couponRpcServiceRpc.freeze(Long.parseLong(taskJob.getTaskId()));
                } else if (taskJob.getTaskType() == TaskJobType.UNFREEZE_COUPON) {
                    runSuccess = couponRpcServiceRpc.unfreeze(Long.parseLong(taskJob.getTaskId()));
                } else {
                    // todo 其他任务类型
                }
                runTask(taskJob, runSuccess);
            }
        } catch (Exception e) {
            XxlJobHelper.log("Redis删除重试任务执行失败: " + e.getMessage());
            logger.error("Redis删除重试任务执行失败", e);
            throw e;
        }
    }

    public void runTask(TaskJob taskJob, boolean runSuccess) {
        if (runSuccess) {
            taskJobMapper.deleteByTaskIdAndTaskType(taskJob.getTaskId(), taskJob.getTaskType());
        } else {
            taskJob.setNextRunTime(calculateNextRetryTime(taskJob.getRunCnt() + 1));
            taskJobMapper.updateById(taskJob);
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