package com.dflong.storecontract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dflong.storecontract.entity.ContractFeeDetailInfo;

/**
 * 合同费用明细表 Service 接口
 */
public interface ContractFeeDetailInfoService extends IService<ContractFeeDetailInfo> {
    
    /**
     * 根据合同项ID查询费用明细
     * @param contractItemId 合同项ID
     * @return 费用明细列表
     */
    java.util.List<ContractFeeDetailInfo> getByContractItemId(String contractItemId);
    
    /**
     * 根据合同ID查询费用明细
     * @param contractId 合同ID
     * @return 费用明细列表
     */
    java.util.List<ContractFeeDetailInfo> getByContractId(String contractId);
    
    /**
     * 更新费用明细状态
     * @param detailId 明细ID
     * @param status 状态
     * @param updateUser 更新人
     * @return 是否成功
     */
    boolean updateStatus(String detailId, Integer status, String updateUser);
}