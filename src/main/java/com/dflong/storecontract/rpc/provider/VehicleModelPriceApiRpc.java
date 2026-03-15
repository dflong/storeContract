package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.goods.VehicleModelPrice;
import com.dflong.storeapi.api.goods.VehicleModelPriceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VehicleModelPriceApiRpc {

    @DubboReference(check = false)
    VehicleModelPriceApi vehicleModelPriceApi;

    public List<VehicleModelPrice> listCalendarPrice(long vehicleModelId, int startDay, int endDay) {
        return vehicleModelPriceApi.listCalendarPrice(vehicleModelId, startDay, endDay);
    }
}
