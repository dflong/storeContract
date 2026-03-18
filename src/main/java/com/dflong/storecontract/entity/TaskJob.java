package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 重试任务实体类
 */
@TableName("task_job")
public class TaskJob {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 任务类型
     */
    private Integer taskType;
    
    /**
     * 任务ID
     */
    private String taskId;
    
    /**
     * 执行参数
     */
    private String extra;
    
    /**
     * 任务状态 1:待执行 2:执行失败 3:执行成功
     */
    private Integer taskStatus;
    
    /**
     * 执行次数
     */
    private Integer runCnt;
    
    /**
     * 失败原因
     */
    private String failReason;
    
    /**
     * 上次执行时间
     */
    private Date lastRunTime;
    
    /**
     * 下次执行时间
     */
    private Date nextRunTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;

    // 构造函数
    public TaskJob() {
    }

    // getter和setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getRunCnt() {
        return runCnt;
    }

    public void setRunCnt(Integer runCnt) {
        this.runCnt = runCnt;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Date getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public Date getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TaskJob{" +
                "id=" + id +
                ", taskType=" + taskType +
                ", taskId='" + taskId + '\'' +
                ", extra='" + extra + '\'' +
                ", taskStatus=" + taskStatus +
                ", runCnt=" + runCnt +
                ", failReason='" + failReason + '\'' +
                ", lastRunTime=" + lastRunTime +
                ", nextRunTime=" + nextRunTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
