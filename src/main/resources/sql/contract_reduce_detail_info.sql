-- 创建合同减免明细信息表
CREATE TABLE `contract_reduce_detail_info` (
  `reduce_detail_id` varchar(32) NOT NULL COMMENT '减免明细ID',
  `contract_item_id` varchar(32) NOT NULL DEFAULT '' COMMENT '合同项ID',
  `contract_id` varchar(32) NOT NULL DEFAULT '' COMMENT '合同ID',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '类型 1：优惠券 2：立减活动 3：会员卡',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1:进行中 2：已取消',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '减免金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`reduce_detail_id`),
  KEY `idx_contract_item_id` (`contract_item_id`),
  KEY `idx_contract_id` (`contract_id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同减免明细信息表';

-- 减免类型枚举说明
-- 1: 优惠券 - 使用优惠券产生的减免
-- 2: 立减活动 - 参与立减活动产生的减免
-- 3: 会员卡 - 使用会员卡权益产生的减免

-- 减免状态枚举说明
-- 1: 进行中 - 减免正在进行中
-- 2: 已取消 - 减免已取消