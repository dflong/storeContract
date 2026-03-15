-- 创建合同项信息表
CREATE TABLE `contract_item_info` (
  `contract_item_id` varchar(32) NOT NULL COMMENT '合同项ID',
  `contract_id` varchar(32) NOT NULL DEFAULT '' COMMENT '合同ID',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '类型 1：下单 2：续租',
  `pay_no` varchar(32) NOT NULL DEFAULT '' COMMENT '支付订单号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1:进行中 2：已取消',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '合同项金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`contract_item_id`),
  KEY `idx_contract_id` (`contract_id`),
  KEY `idx_pay_no` (`pay_no`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同项信息表';

-- 合同项类型枚举说明
-- 1: 下单 - 首次下单创建的合同项
-- 2: 续租 - 续租时创建的合同项

-- 合同项状态枚举说明
-- 1: 进行中 - 合同项正在进行中
-- 2: 已取消 - 合同项已取消