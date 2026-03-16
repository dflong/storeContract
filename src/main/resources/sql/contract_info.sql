-- 创建合同信息表
CREATE TABLE `contract_info` (
  `contract_id` varchar(32) NOT NULL COMMENT '合同ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '合同状态 1:备车中 2：待取车 3：用车中 4：还车中 5：已还车 6：已完成 7：已取消',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '合同金额',
  `vehicle_model_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '车型ID',
  `energy_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '能源类型 1:纯油 2:混动 3:纯电 4:增程',
  `vin` varchar(32) NOT NULL DEFAULT '' COMMENT '车架号',
  `rent_day` int(11) NOT NULL DEFAULT '0' COMMENT '租车天数',
  `fee_type_ids` varchar(64) NOT NULL DEFAULT '' COMMENT '费用类型',
  `contract_start_time` datetime NOT NULL COMMENT '合同开始时间',
  `contract_end_time` datetime NOT NULL COMMENT '合同结束时间',
  `billing_start_time` datetime NOT NULL COMMENT '计费开始时间',
  `billing_end_time` datetime NOT NULL COMMENT '计费结束时间',
  `delivery_vehicle` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否有送车上门服务 1：有 2：没有',
  `pick_up_vehicle` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否有上门取车服务 1：有 2：没有',
  `pick_store_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '取车门店',
  `return_store_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '还车门店',
  `package_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '套餐id',
  `coupon_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '优惠券id',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除 1：正常 2：已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`contract_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_contract_time` (`contract_start_time`, `contract_end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同信息表';

-- 合同状态枚举说明
-- 1: 备车中 - 合同创建，准备车辆
-- 2: 待取车 - 车辆准备完成，等待用户取车
-- 3: 用车中 - 用户已取车，正在使用
-- 4: 还车中 - 用户开始还车流程
-- 5: 已还车 - 车辆已归还，待结算
-- 6: 已完成 - 合同完成，已结算
-- 7: 已取消 - 合同已取消