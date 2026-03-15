package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.stock.VehicleStockApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class VehicleStockApiRpc {

    @DubboReference(check = false)
    private VehicleStockApi vehicleStockApi;

    public boolean checkStock(int vehicleModelId, int startDay, int endDay, int stockNum) {
        return vehicleStockApi.checkStock(vehicleModelId, startDay, endDay, stockNum);
    }

    public boolean deductStock(int vehicleModelId, int startDay, int endDay, int stockNum) {
        return vehicleStockApi.deductStock(vehicleModelId, startDay, endDay, stockNum);
    }
}
