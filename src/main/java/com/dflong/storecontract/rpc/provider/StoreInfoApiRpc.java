package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.base.StoreInfo;
import com.dflong.storeapi.api.base.StoreInfoApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreInfoApiRpc {

    @DubboReference(check = false)
    private StoreInfoApi storeInfoApi;

    public List<StoreInfo> listByDistance(double longitude, double latitude) {
        return storeInfoApi.listByDistance(longitude, latitude);
    }

    public StoreInfo getStoreInfoById(long storeId) {
        return storeInfoApi.getStoreInfoById(storeId);
    }

    public double getDistance(double longitude, double latitude, double longitude2, double latitude2) {
        return storeInfoApi.getDistance(longitude, latitude, longitude2, latitude2);
    }

}
