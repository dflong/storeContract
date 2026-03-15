package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同费用明细表
 */
@Data
@TableName("contract_fee_detail_info")
public class ContractFeeDetailInfo {
    
    /**
     * 主键
     */
    @TableId(value = "detail_id", type = IdType.INPUT)
    private String detailId;
    
    /**
     * 合同项id
     */
    private String contractItemId;
    
    /**
     * 合同id
     */
    private String contractId;
    
    /**
     * 类型 1：费用类型 1基础服务费 2日租服务费 3畅行服务费 4车辆整备费 5夜间服务费 6送车上门费 7上门取车费 8油电服务费 9还车油费 10还车电费 11租车费 12套餐费
     */
    private Integer type;
    
    /**
     * 状态 1:进行中 2：已取消
     */
    private Integer status;
    
    /**
     * 金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 更新人
     */
    private String updateUser;
}