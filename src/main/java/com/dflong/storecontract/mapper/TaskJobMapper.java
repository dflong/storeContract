package com.dflong.storecontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dflong.storecontract.entity.TaskJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 重试任务Mapper接口
 */
@Mapper
public interface TaskJobMapper extends BaseMapper<TaskJob> {

    /**
     * 查询待处理的重试记录
     */
    List<TaskJob> selectPendingRecords(@Param("status") int status, @Param("lastRunTime") Date lastRunTime);

    void deleteByTaskIdAndTaskType(String taskId, int taskType);
}
