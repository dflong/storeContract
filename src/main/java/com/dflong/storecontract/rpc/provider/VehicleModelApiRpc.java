package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.goods.VehicleModel;
import com.dflong.storeapi.api.goods.VehicleModelApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class VehicleModelApiRpc {

    @DubboReference(check = false)
    VehicleModelApi vehicleModelApi;

    public VehicleModel getById(long vehicleModelId) {
        return vehicleModelApi.getById(vehicleModelId);
    }
}
