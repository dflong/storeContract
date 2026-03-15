package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同减免明细信息实体类
 */
@TableName("contract_reduce_detail_info")
public class ContractReduceDetailInfo {
    
    /**
     * 减免明细ID
     */
    @TableId(value = "reduce_detail_id", type = IdType.INPUT)
    private String reduceDetailId;
    
    /**
     * 合同项ID
     */
    private String contractItemId;
    
    /**
     * 合同ID
     */
    private String contractId;
    
    /**
     * 类型 1：优惠券 2：立减活动 3：会员卡
     */
    private Integer type;
    
    /**
     * 状态 1:进行中 2：已取消
     */
    private Integer status;
    
    /**
     * 减免金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 更新人
     */
    private String updateBy;

    // 构造函数
    public ContractReduceDetailInfo() {
    }

    // getter和setter方法
    public String getReduceDetailId() {
        return reduceDetailId;
    }

    public void setReduceDetailId(String reduceDetailId) {
        this.reduceDetailId = reduceDetailId;
    }

    public String getContractItemId() {
        return contractItemId;
    }

    public void setContractItemId(String contractItemId) {
        this.contractItemId = contractItemId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "ContractReduceDetailInfo{" +
                "reduceDetailId='" + reduceDetailId + '\'' +
                ", contractItemId='" + contractItemId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}