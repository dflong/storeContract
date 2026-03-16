package com.dflong.storecontract.rest.bo;

import com.dflong.storecontract.entity.*;

import java.util.List;

public class CreateContractDB {

    String contractId;

    ContractInfo contractInfo;

    ContractItemInfo contractItemInfo;

    List<ContractDeliveryPickUpInfo> deliveryPickUpInfoList;

    List<ContractFeeDetailInfo> contractFeeDetailInfoList;

    List<ContractReduceDetailInfo> contractReduceDetailInfoList;

    PayInfo payInfo;

    public static CreateContractDB build(ContractInfo contractInfo, ContractItemInfo contractItemInfo,
                                         List<ContractDeliveryPickUpInfo> list, List<ContractFeeDetailInfo> contractFeeDetailInfoList,
                                         List<ContractReduceDetailInfo> reduceFeeBoList, PayInfo payInfo) {
        CreateContractDB contractDB = new CreateContractDB();
        contractDB.setContractInfo(contractInfo);
        contractDB.setContractItemInfo(contractItemInfo);
        contractDB.setDeliveryPickUpInfoList(list);
        contractDB.setContractFeeDetailInfoList(contractFeeDetailInfoList);
        contractDB.setContractReduceDetailInfoList(reduceFeeBoList);
        contractDB.setPayInfo(payInfo);
        return contractDB;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

    public ContractItemInfo getContractItemInfo() {
        return contractItemInfo;
    }

    public void setContractItemInfo(ContractItemInfo contractItemInfo) {
        this.contractItemInfo = contractItemInfo;
    }

    public List<ContractDeliveryPickUpInfo> getDeliveryPickUpInfoList() {
        return deliveryPickUpInfoList;
    }

    public void setDeliveryPickUpInfoList(List<ContractDeliveryPickUpInfo> deliveryPickUpInfoList) {
        this.deliveryPickUpInfoList = deliveryPickUpInfoList;
    }

    public List<ContractFeeDetailInfo> getContractFeeDetailInfoList() {
        return contractFeeDetailInfoList;
    }

    public void setContractFeeDetailInfoList(List<ContractFeeDetailInfo> contractFeeDetailInfoList) {
        this.contractFeeDetailInfoList = contractFeeDetailInfoList;
    }

    public List<ContractReduceDetailInfo> getContractReduceDetailInfoList() {
        return contractReduceDetailInfoList;
    }

    public void setContractReduceDetailInfoList(List<ContractReduceDetailInfo> contractReduceDetailInfoList) {
        this.contractReduceDetailInfoList = contractReduceDetailInfoList;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }
}
