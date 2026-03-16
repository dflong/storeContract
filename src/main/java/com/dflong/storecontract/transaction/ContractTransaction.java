package com.dflong.storecontract.transaction;

import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;
import com.dflong.storecontract.entity.ContractFeeDetailInfo;
import com.dflong.storecontract.entity.ContractReduceDetailInfo;
import com.dflong.storecontract.mapper.*;
import com.dflong.storecontract.rest.bo.CreateContractDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
