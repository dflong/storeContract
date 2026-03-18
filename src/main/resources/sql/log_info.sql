-- 创建操作日志表
CREATE TABLE `log_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `desc` varchar(32) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
