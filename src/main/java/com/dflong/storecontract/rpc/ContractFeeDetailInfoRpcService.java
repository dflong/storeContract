package com.dflong.storecontract.rpc;

import com.dflong.storecontract.entity.ContractFeeDetailInfo;
import java.util.List;

/**
 * 合同费用明细表 RPC 服务接口
 */
public interface ContractFeeDetailInfoRpcService {
    
    /**
     * 新增费用明细
     */
    boolean addContractFeeDetail(ContractFeeDetailInfo contractFeeDetailInfo);
    
    /**
     * 修改费用明细
     */
    boolean updateContractFeeDetail(ContractFeeDetailInfo contractFeeDetailInfo);
    
    /**
     * 删除费用明细
     */
    boolean deleteContractFeeDetail(String detailId);
    
    /**
     * 根据ID查询费用明细
     */
    ContractFeeDetailInfo getContractFeeDetailById(String detailId);
    
    /**
     * 根据合同项ID查询费用明细
     */
    List<ContractFeeDetailInfo> getContractFeeDetailByItemId(String contractItemId);
    
    /**
     * 根据合同ID查询费用明细
     */
    List<ContractFeeDetailInfo> getContractFeeDetailByContractId(String contractId);
    
    /**
     * 更新费用明细状态
     */
    boolean updateContractFeeDetailStatus(String detailId, Integer status, String updateUser);
}