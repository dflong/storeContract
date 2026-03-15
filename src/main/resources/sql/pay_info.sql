-- 创建支付信息表
CREATE TABLE `pay_info` (
  `pay_no` varchar(32) NOT NULL COMMENT '支付订单号',
  `contract_id` varchar(32) NOT NULL DEFAULT '' COMMENT '合同ID',
  `contract_item_id` varchar(32) NOT NULL DEFAULT '' COMMENT '合同项ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '支付状态 1:待支付 2：已支付 3：已取消',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`pay_no`),
  KEY `idx_contract_id` (`contract_id`),
  KEY `idx_contract_item_id` (`contract_item_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付信息表';

-- 支付状态枚举说明
-- 1: 待支付 - 支付订单创建，等待支付
-- 2: 已支付 - 支付成功
-- 3: 已取消 - 支付订单已取消