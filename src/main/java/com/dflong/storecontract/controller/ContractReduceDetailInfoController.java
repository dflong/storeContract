package com.dflong.storecontract.controller;

import com.dflong.storecontract.entity.ContractReduceDetailInfo;
import com.dflong.storecontract.service.ContractReduceDetailInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同减免明细信息控制器
 */
@RestController
@RequestMapping("/api/contract-reduce-detail")
public class ContractReduceDetailInfoController {

    @Autowired
    private ContractReduceDetailInfoService contractReduceDetailInfoService;

    /**
     * 根据减免明细ID查询减免明细信息
     */
    @GetMapping("/{reduceDetailId}")
    public JSONObject getReduceDetailById(@PathVariable String reduceDetailId) {
        JSONObject result = new JSONObject();
        try {
            ContractReduceDetailInfo reduceDetail = contractReduceDetailInfoService.getByReduceDetailId(reduceDetailId);
            if (reduceDetail != null) {
                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", reduceDetail);
            } else {
                result.put("code", 404);
                result.put("message", "减免明细不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据合同项ID查询减免明细列表
     */
    @GetMapping("/contract-item/{contractItemId}")
    public JSONObject getReduceDetailsByContractItemId(@PathVariable String contractItemId) {
        JSONObject result = new JSONObject();
        try {
            List<ContractReduceDetailInfo> reduceDetails = contractReduceDetailInfoService.getByContractItemId(contractItemId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", reduceDetails);
            result.put("total", reduceDetails.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据合同ID查询减免明细列表
     */
    @GetMapping("/contract/{contractId}")
    public JSONObject getReduceDetailsByContractId(@PathVariable String contractId) {
        JSONObject result = new JSONObject();
        try {
            List<ContractReduceDetailInfo> reduceDetails = contractReduceDetailInfoService.getByContractId(contractId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", reduceDetails);
            result.put("total", reduceDetails.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据类型查询减免明细列表
     */
    @GetMapping("/type/{type}")
    public JSONObject getReduceDetailsByType(@PathVariable Integer type) {
        JSONObject result = new JSONObject();
        try {
            List<ContractReduceDetailInfo> reduceDetails = contractReduceDetailInfoService.getByType(type);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", reduceDetails);
            result.put("total", reduceDetails.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据状态查询减免明细列表
     */
    @GetMapping("/status/{status}")
    public JSONObject getReduceDetailsByStatus(@PathVariable Integer status) {
        JSONObject result = new JSONObject();
        try {
            List<ContractReduceDetailInfo> reduceDetails = contractReduceDetailInfoService.getByStatus(status);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", reduceDetails);
            result.put("total", reduceDetails.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有减免明细信息
     */
    @GetMapping("/list")
    public JSONObject getAllReduceDetails() {
        JSONObject result = new JSONObject();
        try {
            List<ContractReduceDetailInfo> reduceDetails = contractReduceDetailInfoService.getAllReduceDetails();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", reduceDetails);
            result.put("total", reduceDetails.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 创建减免明细信息
     */
    @PostMapping("/create")
    public JSONObject createReduceDetail(@RequestBody ContractReduceDetailInfo contractReduceDetailInfo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractReduceDetailInfoService.createReduceDetail(contractReduceDetailInfo);
            if (success) {
                result.put("code", 200);
                result.put("message", "创建成功");
            } else {
                result.put("code", 500);
                result.put("message", "创建失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "创建失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新减免明细信息
     */
    @PutMapping("/update")
    public JSONObject updateReduceDetail(@RequestBody ContractReduceDetailInfo contractReduceDetailInfo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractReduceDetailInfoService.updateReduceDetail(contractReduceDetailInfo);
            if (success) {
                result.put("code", 200);
                result.put("message", "更新成功");
            } else {
                result.put("code", 404);
                result.put("message", "减免明细不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除减免明细信息
     */
    @DeleteMapping("/{reduceDetailId}")
    public JSONObject deleteReduceDetail(@PathVariable String reduceDetailId) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractReduceDetailInfoService.deleteReduceDetail(reduceDetailId);
            if (success) {
                result.put("code", 200);
                result.put("message", "删除成功");
            } else {
                result.put("code", 404);
                result.put("message", "减免明细不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新减免明细状态
     */
    @PutMapping("/{reduceDetailId}/status/{status}")
    public JSONObject updateReduceDetailStatus(@PathVariable String reduceDetailId, @PathVariable Integer status) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractReduceDetailInfoService.updateStatus(reduceDetailId, status);
            if (success) {
                result.put("code", 200);
                result.put("message", "状态更新成功");
            } else {
                result.put("code", 404);
                result.put("message", "减免明细不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "状态更新失败：" + e.getMessage());
        }
        return result;
    }
}