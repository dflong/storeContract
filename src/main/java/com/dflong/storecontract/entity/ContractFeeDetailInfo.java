package com.dflong.storecontract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dflong.storeapi.api.ConstantStatus;
import com.dflong.storeapi.api.FeeTypeEnum;
import com.dflong.storeapi.api.contract.ServiceFeeBo;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static List<ContractFeeDetailInfo> build(List<String> detailIds, String contractItemId, String contractId, long userId, LocalDateTime now,
                                                    List<ServiceFeeBo> serviceFeeBoList, ContractDeliveryPickUpInfo deliveryInfo, ContractDeliveryPickUpInfo pickUpInfo) {
        List<ContractFeeDetailInfo> res = new ArrayList<>();

        for (int i = 0; i < serviceFeeBoList.size(); i++) {
            ContractFeeDetailInfo detailInfo = new ContractFeeDetailInfo();

            ServiceFeeBo serviceFeeBo = serviceFeeBoList.get(i);
            detailInfo.setDetailId(detailIds.get(i));
            if (serviceFeeBo.getFeeType() == FeeTypeEnum.DELIVERY_VEHICLE_FEE.getCode()) {
                detailInfo.setContractItemId(deliveryInfo.getDeliveryPickUpId());
            } else if (serviceFeeBo.getFeeType() == FeeTypeEnum.PICK_UP_VEHICLE_FEE.getCode()) {
                detailInfo.setContractItemId(pickUpInfo.getDeliveryPickUpId());
            } else {
                detailInfo.setContractItemId(contractItemId);
            }
            detailInfo.setContractId(contractId);
            detailInfo.setType(serviceFeeBo.getFeeType());
            detailInfo.setStatus(ConstantStatus.SUCCESS.getCode());
            detailInfo.setTotalAmount(serviceFeeBo.getPrice());

            detailInfo.setCreateUser(userId + "");
            detailInfo.setCreateTime(now);
            detailInfo.setUpdateUser(userId + "");
            detailInfo.setUpdateTime(now);

            res.add(detailInfo);
        }

        return res;
    }
}