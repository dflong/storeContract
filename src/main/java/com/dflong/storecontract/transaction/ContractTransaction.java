package com.dflong.storecontract.transaction;

import com.dflong.storeapi.api.ConstantStatus;
import com.dflong.storeapi.api.PayStatus;
import com.dflong.storecontract.constant.TaskJobType;
import com.dflong.storecontract.entity.*;
import com.dflong.storecontract.manage.DateUtils;
import com.dflong.storecontract.mapper.*;
import com.dflong.storecontract.rest.bo.CreateContractDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ContractTransaction {

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    private ContractItemInfoMapper contractItemInfoMapper;

    @Autowired
    private ContractDeliveryPickUpInfoMapper contractDeliveryPickUpInfoMapper;

    @Autowired
    private ContractFeeDetailInfoMapper contractFeeDetailInfoMapper;

    @Autowired
    private ContractReduceDetailInfoMapper contractReduceDetailInfoMapper;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private TaskJobMapper taskJobMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean createContract(CreateContractDB createContractDB) {
        contractInfoMapper.insert(createContractDB.getContractInfo());
        contractItemInfoMapper.insert(createContractDB.getContractItemInfo());
        List<ContractDeliveryPickUpInfo> deliveryPickUpInfoList = createContractDB.getDeliveryPickUpInfoList();
        if (deliveryPickUpInfoList != null && !deliveryPickUpInfoList.isEmpty()) {
            for (ContractDeliveryPickUpInfo contractDeliveryPickUpInfo : deliveryPickUpInfoList) {
                contractDeliveryPickUpInfoMapper.insert(contractDeliveryPickUpInfo);
            }
        }
        List<ContractFeeDetailInfo> contractFeeDetailInfoList = createContractDB.getContractFeeDetailInfoList();
        if (contractFeeDetailInfoList != null && !contractFeeDetailInfoList.isEmpty()) {
            for (ContractFeeDetailInfo detailInfo : contractFeeDetailInfoList) {
                contractFeeDetailInfoMapper.insert(detailInfo);
            }
        }

        List<ContractReduceDetailInfo> contractReduceDetailInfoList = createContractDB.getContractReduceDetailInfoList();
        if (contractReduceDetailInfoList != null && !contractReduceDetailInfoList.isEmpty()) {
            for (ContractReduceDetailInfo contractReduceDetailInfo : contractReduceDetailInfoList) {
                contractReduceDetailInfoMapper.insert(contractReduceDetailInfo);
            }
        }

        payInfoMapper.insert(createContractDB.getPayInfo());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(ContractInfo contractInfo) {
        String contractId = contractInfo.getContractId();
        // rpc服务先插入db表，执行成功后删除，否则表兜底
        LocalDateTime now = LocalDateTime.now();
        TaskJob freezeCoupon = new TaskJob();
        freezeCoupon.setTaskType(TaskJobType.FREEZE_COUPON);
        freezeCoupon.setTaskId(contractInfo.getCouponId() + "");
        freezeCoupon.setExtra(contractId);
        freezeCoupon.setTaskStatus(ConstantStatus.SUCCESS.getCode());
        freezeCoupon.setNextRunTime(DateUtils.fromLocalDateTime(now.plusSeconds(5)));
        freezeCoupon.setCreateTime(DateUtils.fromLocalDateTime(now));
        freezeCoupon.setUpdateTime(DateUtils.fromLocalDateTime(now));
        taskJobMapper.insert(freezeCoupon);
    }
}
