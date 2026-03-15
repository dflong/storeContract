-- 车辆送取表
CREATE TABLE contract_delivery_pick_up_info (
    delivery_pick_up_id VARCHAR(32) NOT NULL COMMENT '车辆送取id',
    contract_id VARCHAR(32) NOT NULL DEFAULT '' COMMENT '合同id',
    type TINYINT NOT NULL DEFAULT 0 COMMENT '类型 1：送车上门 2：上门取车',
    pay_no VARCHAR(32) NOT NULL DEFAULT '' COMMENT '支付订单号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 1:进行中 2：已取消',
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '金额',
    longitude DOUBLE DEFAULT NULL COMMENT '经度',
    latitude DOUBLE DEFAULT NULL COMMENT '纬度',
    distance DOUBLE DEFAULT NULL COMMENT '距离（米）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_user VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_user VARCHAR(32) NOT NULL DEFAULT '' COMMENT '更新人',
    PRIMARY KEY (delivery_pick_up_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆送取表';

-- 创建索引
CREATE INDEX idx_contract_id ON contract_delivery_pick_up_info(contract_id);
CREATE INDEX idx_type ON contract_delivery_pick_up_info(type);
CREATE INDEX idx_pay_no ON contract_delivery_pick_up_info(pay_no);
CREATE INDEX idx_status ON contract_delivery_pick_up_info(status);
CREATE INDEX idx_create_time ON contract_delivery_pick_up_info(create_time);