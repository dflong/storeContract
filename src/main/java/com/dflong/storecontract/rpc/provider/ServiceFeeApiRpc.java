package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.goods.ServiceFee;
import com.dflong.storeapi.api.goods.ServiceFeeApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceFeeApiRpc {

    @DubboReference(check = false)
    ServiceFeeApi serviceFeeApi;

    public List<ServiceFee> listByVehicleModel(long vehicleModelId) {
        return serviceFeeApi.listByVehicleModel(vehicleModelId);
    }
}
