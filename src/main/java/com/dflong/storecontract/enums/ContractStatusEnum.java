package com.dflong.storecontract.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合同状态枚举
 */
public enum ContractStatusEnum {
    
    PREPARING_VEHICLE(1, "备车中"),
    WAITING_PICKUP(2, "待取车"),
    USING_VEHICLE(3, "用车中"),
    RETURNING_VEHICLE(4, "还车中"),
    VEHICLE_RETURNED(5, "已还车"),
    COMPLETED(6, "已完成"),
    CANCELLED(7, "已取消");
    
    private final Integer code;
    private final String desc;
    
    ContractStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }

    /**
     * 进行中状态列表（备车中、待取车、用车中、还车中、已还车）
     */
    public static final List<Integer> RUNNING_STATUS =
            Arrays.asList(PREPARING_VEHICLE.getCode(), WAITING_PICKUP.getCode(), USING_VEHICLE.getCode(), RETURNING_VEHICLE.getCode(), VEHICLE_RETURNED.getCode());

    /**
     * 终态列表（已完成、已取消）
     */
    public static final List<Integer> FINAL_STATUS =
            Arrays.asList(COMPLETED.getCode(), CANCELLED.getCode());

    /**
     * 检查是否为进行中状态
     */
    public static boolean isRunningStatus(int status) {
        return RUNNING_STATUS.contains(status);
    }

    /**
     * 检查是否为终态（不可再流转的状态）
     */
    public static boolean isFinalStatus(int status) {
        return FINAL_STATUS.contains(status); // 已完成、已取消为终态
    }

    

    /**
     * 获取可流转的状态列表
     */
    public static List<Integer> getAvailableTransitions(int currentStatus) {
        List<Integer> availableStatus = new ArrayList<>();
        
        switch (currentStatus) {
            case 1: // 备车中
                availableStatus.add(2); // 待取车
                availableStatus.add(7); // 已取消
                break;
            case 2: // 待取车
                availableStatus.add(3); // 用车中
                availableStatus.add(7); // 已取消
                break;
            case 3: // 用车中
                availableStatus.add(4); // 还车中
                break;
            case 4: // 还车中
                availableStatus.add(5); // 已还车
                break;
            case 5: // 已还车
                availableStatus.add(6); // 已完成
                break;
            case 6: // 已完成
                // 已完成状态不可再流转
                break;
            case 7: // 已取消
                // 已取消状态不可再流转
                break;
            default:
                break;
        }
        
        return availableStatus;
    }
    
    /**
     * 根据code获取描述
     */
    public static String getDescByCode(int code) {
        ContractStatusEnum status = getByCode(code);
        return status != null ? status.getDesc() : "";
    }

    /**
     * 根据code获取枚举
     */
    public static ContractStatusEnum getByCode(int code) {
        if (code == 0) {
            return null;
        }
        for (ContractStatusEnum status : ContractStatusEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}