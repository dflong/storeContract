package com.dflong.storecontract.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dflong.storecontract.entity.ContractDeliveryPickUpInfo;
import com.dflong.storecontract.service.ContractDeliveryPickUpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 车辆送取表 Controller
 */
@RestController
@RequestMapping("/contractDeliveryPickUpInfo")
public class ContractDeliveryPickUpInfoController {

    @Autowired
    private ContractDeliveryPickUpInfoService contractDeliveryPickUpInfoService;

    /**
     * 新增送取信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody ContractDeliveryPickUpInfo contractDeliveryPickUpInfo) {
        boolean result = contractDeliveryPickUpInfoService.save(contractDeliveryPickUpInfo);
        return result ? Result.success("新增成功") : Result.error("新增失败");
    }

    /**
     * 修改送取信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody ContractDeliveryPickUpInfo contractDeliveryPickUpInfo) {
        boolean result = contractDeliveryPickUpInfoService.updateById(contractDeliveryPickUpInfo);
        return result ? Result.success("修改成功") : Result.error("修改失败");
    }

    /**
     * 删除送取信息
     */
    @PostMapping("/delete/{deliveryPickUpId}")
    public Result delete(@PathVariable String deliveryPickUpId) {
        boolean result = contractDeliveryPickUpInfoService.removeById(deliveryPickUpId);
        return result ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 根据ID查询送取信息
     */
    @GetMapping("/getById/{deliveryPickUpId}")
    public Result getById(@PathVariable String deliveryPickUpId) {
        ContractDeliveryPickUpInfo contractDeliveryPickUpInfo = contractDeliveryPickUpInfoService.getById(deliveryPickUpId);
        return contractDeliveryPickUpInfo != null ? Result.success(contractDeliveryPickUpInfo) : Result.error("数据不存在");
    }

    /**
     * 分页查询送取信息
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       ContractDeliveryPickUpInfo query) {
        Page<ContractDeliveryPickUpInfo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ContractDeliveryPickUpInfo> queryWrapper = new QueryWrapper<>();
        
        if (query != null) {
            if (query.getContractId() != null && !query.getContractId().isEmpty()) {
                queryWrapper.eq("contract_id", query.getContractId());
            }
            if (query.getType() != null) {
                queryWrapper.eq("type", query.getType());
            }
            if (query.getPayNo() != null && !query.getPayNo().isEmpty()) {
                queryWrapper.eq("pay_no", query.getPayNo());
            }
            if (query.getStatus() != null) {
                queryWrapper.eq("status", query.getStatus());
            }
        }
        
        queryWrapper.orderByDesc("create_time");
        IPage<ContractDeliveryPickUpInfo> result = contractDeliveryPickUpInfoService.page(page, queryWrapper);
        return Result.success(result);
    }

    /**
     * 根据合同ID查询送取信息
     */
    @GetMapping("/getByContractId/{contractId}")
    public Result getByContractId(@PathVariable String contractId) {
        List<ContractDeliveryPickUpInfo> list = contractDeliveryPickUpInfoService.getByContractId(contractId);
        return Result.success(list);
    }

    /**
     * 根据合同ID和类型查询送取信息
     */
    @GetMapping("/getByContractIdAndType")
    public Result getByContractIdAndType(@RequestParam String contractId, 
                                        @RequestParam Integer type) {
        ContractDeliveryPickUpInfo info = contractDeliveryPickUpInfoService.getByContractIdAndType(contractId, type);
        return info != null ? Result.success(info) : Result.error("数据不存在");
    }

    /**
     * 根据支付订单号查询送取信息
     */
    @GetMapping("/getByPayNo/{payNo}")
    public Result getByPayNo(@PathVariable String payNo) {
        ContractDeliveryPickUpInfo info = contractDeliveryPickUpInfoService.getByPayNo(payNo);
        return info != null ? Result.success(info) : Result.error("数据不存在");
    }

    /**
     * 更新送取信息状态
     */
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam String deliveryPickUpId, 
                               @RequestParam Integer status,
                               @RequestParam String updateUser) {
        boolean result = contractDeliveryPickUpInfoService.updateStatus(deliveryPickUpId, status, updateUser);
        return result ? Result.success("状态更新成功") : Result.error("状态更新失败");
    }

    /**
     * 通用返回结果类
     */
    public static class Result {
        private boolean success;
        private String message;
        private Object data;

        public Result(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public static Result success(Object data) {
            return new Result(true, "操作成功", data);
        }

        public static Result success(String message) {
            return new Result(true, message, null);
        }

        public static Result error(String message) {
            return new Result(false, message, null);
        }

        // getter and setter methods
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }
}