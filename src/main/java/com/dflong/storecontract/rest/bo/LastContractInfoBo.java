package com.dflong.storecontract.rest.bo;

import java.io.Serializable;

public class LastContractInfoBo implements Serializable {

    private String contractId;

    private int status;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
