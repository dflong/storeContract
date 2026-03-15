package com.dflong.storecontract.enums;

/**
 * 费用类型枚举
 */
public enum FeeTypeEnum {
    
    BASIC_SERVICE_FEE(1, "基础服务费"),
    DAILY_RENTAL_FEE(2, "日租服务费"),
    UNLIMITED_TRAVEL_FEE(3, "畅行服务费"),
    VEHICLE_PREPARATION_FEE(4, "车辆整备费"),
    NIGHT_SERVICE_FEE(5, "夜间服务费"),
    DELIVERY_VEHICLE_FEE(6, "送车上门费"),
    PICK_UP_VEHICLE_FEE(7, "上门取车费"),
    OIL_ELECTRICITY_SERVICE_FEE(8, "油电服务费"),
    RETURN_OIL_FEE(9, "还车油费"),
    RETURN_ELECTRICITY_FEE(10, "还车电费"),
    CAR_RENTAL_FEE(11, "租车费"),
    PACKAGE_FEE(12, "套餐费"),
    COUPON(13, "优惠券"),
    REDUCTION_ACTIVITY(14, "立减活动"),
    MEMBERSHIP_CARD(15, "会员卡"),
    SUI_XIANS_CARD(16, "随享卡"); // 可以根据实际业务需求添加更多费用类型
    
    private final Integer code;
    private final String desc;
    
    FeeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    /**
     * 根据code获取枚举
     */
    public static FeeTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (FeeTypeEnum type : FeeTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 根据code获取描述
     */
    public static String getDescByCode(Integer code) {
        FeeTypeEnum type = getByCode(code);
        return type != null ? type.getDesc() : "";
    }
    
    /**
     * 检查code是否存在
     */
    public static boolean contains(Integer code) {
        return getByCode(code) != null;
    }
    
    /**
     * 获取所有费用类型
     */
    public static java.util.Map<Integer, String> getAllTypes() {
        java.util.Map<Integer, String> map = new java.util.HashMap<>();
        for (FeeTypeEnum type : FeeTypeEnum.values()) {
            map.put(type.getCode(), type.getDesc());
        }
        return map;
    }
}