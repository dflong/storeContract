package com.dflong.storecontract.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dflong.storecontract.entity.ContractFeeDetailInfo;
import com.dflong.storecontract.service.ContractFeeDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 合同费用明细表 Controller
 */
@RestController
@RequestMapping("/contractFeeDetailInfo")
public class ContractFeeDetailInfoController {

    @Autowired
    private ContractFeeDetailInfoService contractFeeDetailInfoService;

    /**
     * 新增费用明细
     */
    @PostMapping("/add")
    public Result add(@RequestBody ContractFeeDetailInfo contractFeeDetailInfo) {
        boolean result = contractFeeDetailInfoService.save(contractFeeDetailInfo);
        return result ? Result.success("新增成功") : Result.error("新增失败");
    }

    /**
     * 修改费用明细
     */
    @PostMapping("/update")
    public Result update(@RequestBody ContractFeeDetailInfo contractFeeDetailInfo) {
        boolean result = contractFeeDetailInfoService.updateById(contractFeeDetailInfo);
        return result ? Result.success("修改成功") : Result.error("修改失败");
    }

    /**
     * 删除费用明细
     */
    @PostMapping("/delete/{detailId}")
    public Result delete(@PathVariable String detailId) {
        boolean result = contractFeeDetailInfoService.removeById(detailId);
        return result ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 根据ID查询费用明细
     */
    @GetMapping("/getById/{detailId}")
    public Result getById(@PathVariable String detailId) {
        ContractFeeDetailInfo contractFeeDetailInfo = contractFeeDetailInfoService.getById(detailId);
        return contractFeeDetailInfo != null ? Result.success(contractFeeDetailInfo) : Result.error("数据不存在");
    }

    /**
     * 分页查询费用明细
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       ContractFeeDetailInfo query) {
        Page<ContractFeeDetailInfo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ContractFeeDetailInfo> queryWrapper = new QueryWrapper<>();
        
        if (query != null) {
            if (query.getContractItemId() != null && !query.getContractItemId().isEmpty()) {
                queryWrapper.eq("contract_item_id", query.getContractItemId());
            }
            if (query.getContractId() != null && !query.getContractId().isEmpty()) {
                queryWrapper.eq("contract_id", query.getContractId());
            }
            if (query.getType() != null) {
                queryWrapper.eq("type", query.getType());
            }
            if (query.getStatus() != null) {
                queryWrapper.eq("status", query.getStatus());
            }
        }
        
        queryWrapper.orderByDesc("create_time");
        IPage<ContractFeeDetailInfo> result = contractFeeDetailInfoService.page(page, queryWrapper);
        return Result.success(result);
    }

    /**
     * 根据合同项ID查询费用明细
     */
    @GetMapping("/getByContractItemId/{contractItemId}")
    public Result getByContractItemId(@PathVariable String contractItemId) {
        List<ContractFeeDetailInfo> list = contractFeeDetailInfoService.getByContractItemId(contractItemId);
        return Result.success(list);
    }

    /**
     * 根据合同ID查询费用明细
     */
    @GetMapping("/getByContractId/{contractId}")
    public Result getByContractId(@PathVariable String contractId) {
        List<ContractFeeDetailInfo> list = contractFeeDetailInfoService.getByContractId(contractId);
        return Result.success(list);
    }

    /**
     * 更新费用明细状态
     */
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam String detailId, 
                               @RequestParam Integer status,
                               @RequestParam String updateUser) {
        boolean result = contractFeeDetailInfoService.updateStatus(detailId, status, updateUser);
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