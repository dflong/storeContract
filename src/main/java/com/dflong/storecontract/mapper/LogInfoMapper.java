package com.dflong.storecontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dflong.storecontract.entity.LogInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志信息Mapper接口
 */
@Mapper
public interface LogInfoMapper extends BaseMapper<LogInfo> {
    
}