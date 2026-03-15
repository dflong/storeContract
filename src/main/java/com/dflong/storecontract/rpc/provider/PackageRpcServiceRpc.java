package com.dflong.storecontract.rpc.provider;

import com.dflong.storeapi.api.act.Package;
import com.dflong.storeapi.api.act.PackageRpcService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PackageRpcServiceRpc {

    @DubboReference(check = false)
    PackageRpcService packageRpcService;

    public List<Package> listByVehicleModelIdAndDayRange(long vehicleModelId, int startDay, int endDay) {
        return packageRpcService.listByVehicleModelIdAndDayRange(vehicleModelId, startDay, endDay);
    }

}
