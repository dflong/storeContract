package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dflong.storeapi.api.ConstantItemTypeStatus;
import com.dflong.storeapi.api.ConstantStatus;
import com.dflong.storeapi.api.billing.CreateContractBillingBo;
import com.dflong.storeapi.api.contract.StoreInfBo;
import com.dflong.storecontract.manage.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 合同项信息实体类
 */
@TableName("contract_item_info")
public class ContractItemInfo {
    
    /**
     * 合同项ID
     */
    @TableId(value = "contract_item_id", type = IdType.INPUT)
    private String contractItemId;
    
    /**
     * 合同ID
     */
    private String contractId;
    
    /**
     * 类型 1：下单 2：续租
     */
    private Integer type;
    
    /**
     * 支付订单号
     */
    private String payNo;
    
    /**
     * 状态 1:进行中 2：已取消
     */
    private Integer status;
    
    /**
     * 合同项金额
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
    public ContractItemInfo() {
    }

    public static ContractItemInfo build(String contractItemId, LocalDateTime now, CreateContractBillingBo billingBo, String payNo, long userId) {
        ContractItemInfo contractItemInfo = new ContractItemInfo();

        contractItemInfo.setContractItemId(contractItemId);
        contractItemInfo.setContractId(contractItemId);
        contractItemInfo.setType(ConstantItemTypeStatus.CREATE.getCode());
        contractItemInfo.setPayNo(payNo);
        contractItemInfo.setStatus(ConstantStatus.SUCCESS.getCode());
        StoreInfBo storeInfBo = billingBo.getStoreInfBo();
        contractItemInfo.setTotalAmount(billingBo.getTotalAmount().subtract(storeInfBo.getPickUpPrice()).subtract(storeInfBo.getReturnPrice())); // 总金额减去取还车费用

        contractItemInfo.setCreateBy(userId + "");
        contractItemInfo.setCreateTime(DateUtils.fromLocalDateTime(now));
        contractItemInfo.setUpdateBy(userId + "");
        contractItemInfo.setUpdateTime(DateUtils.fromLocalDateTime(now));
        return contractItemInfo;
    }

    // getter和setter方法
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

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
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
        return "ContractItemInfo{" +
                "contractItemId='" + contractItemId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", type=" + type +
                ", payNo='" + payNo + '\'' +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}