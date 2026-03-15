-- 合同费用明细表
CREATE TABLE contract_fee_detail_info (
    detail_id VARCHAR(32) NOT NULL COMMENT '主键',
    contract_item_id VARCHAR(32) NOT NULL DEFAULT '' COMMENT '合同项id',
    contract_id VARCHAR(32) NOT NULL DEFAULT '' COMMENT '合同id',
    type TINYINT NOT NULL DEFAULT 0 COMMENT '类型 1：费用类型 1基础服务费 2日租服务费 3畅行服务费 4车辆整备费 5夜间服务费 6送车上门费 7上门取车费 8油电服务费 9还车油费 10还车电费 11租车费 12套餐费',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 1:进行中 2：已取消',
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '金额',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_user VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_user VARCHAR(32) NOT NULL DEFAULT '' COMMENT '更新人',
    PRIMARY KEY (detail_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同费用明细表';

-- 创建索引
CREATE INDEX idx_contract_item_id ON contract_fee_detail_info(contract_item_id);
CREATE INDEX idx_contract_id ON contract_fee_detail_info(contract_id);
CREATE INDEX idx_type ON contract_fee_detail_info(type);
CREATE INDEX idx_status ON contract_fee_detail_info(status);
CREATE INDEX idx_create_time ON contract_fee_detail_info(create_time);