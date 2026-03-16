package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dflong.storeapi.api.PayStatus;
import com.dflong.storecontract.manage.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 支付信息实体类
 */
@TableName("pay_info")
public class PayInfo {
    
    /**
     * 支付订单号
     */
    @TableId(value = "pay_no", type = IdType.INPUT)
    private String payNo;
    
    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 支付状态 1:待支付 2：已支付 3：已取消
     */
    private Integer status;
    
    /**
     * 支付金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付时间
     */
    private Date payTime;

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
    public PayInfo() {
    }

    public static PayInfo build(String payNo, String contractId, BigDecimal totalAmount, LocalDateTime now, long userId, int lazyPay) {
        PayInfo info = new PayInfo();
        info.setPayNo(payNo);
        info.setContractId(contractId);
        info.setStatus(PayStatus.WAIT_PAY.getCode());
        info.setTotalAmount(totalAmount);
        info.setPayTime(DateUtils.fromLocalDateTime(now.plusMinutes(lazyPay)));

        info.setCreateBy(userId + "");
        info.setCreateTime(DateUtils.fromLocalDateTime(now));
        info.setUpdateBy(userId + "");
        info.setUpdateTime(DateUtils.fromLocalDateTime(now));
        return info;
    }

    // getter和setter方法
    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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
        return "PayInfo{" +
                "payNo='" + payNo + '\'' +
                ", contractId='" + contractId + '\'' +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", payTime=" + payTime +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}