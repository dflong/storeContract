-- 创建重试任务表
CREATE TABLE `task_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_type` tinyint(4) NOT NULL COMMENT '任务类型',
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `extra` varchar(512) NOT NULL DEFAULT '' COMMENT '执行参数',
  `task_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '任务状态 1:待执行 2:执行失败 3:执行成功',
  `run_cnt` int(11) NOT NULL DEFAULT '0' COMMENT '执行次数',
  `fail_reason` varchar(512) NOT NULL DEFAULT '' COMMENT '失败原因',
  `last_run_time` datetime DEFAULT NULL COMMENT '上次执行时间',
  `next_run_time` datetime DEFAULT NULL COMMENT '下次执行时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_next_run_time_task_status` (`next_run_time`, `task_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='重试任务表';

-- 任务状态枚举说明
-- 1: 待执行 - 任务创建，等待执行
-- 2: 执行失败 - 任务执行失败，需要重试
-- 3: 执行成功 - 任务执行成功
