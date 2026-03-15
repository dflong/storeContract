package com.dflong.storecontract.controller;

import com.dflong.storecontract.entity.ContractItemInfo;
import com.dflong.storecontract.service.ContractItemInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同项信息控制器
 */
@RestController
@RequestMapping("/api/contract-item")
public class ContractItemInfoController {

    @Autowired
    private ContractItemInfoService contractItemInfoService;

    /**
     * 根据合同项ID查询合同项信息
     */
    @GetMapping("/{contractItemId}")
    public JSONObject getContractItemById(@PathVariable String contractItemId) {
        JSONObject result = new JSONObject();
        try {
            ContractItemInfo contractItemInfo = contractItemInfoService.getByContractItemId(contractItemId);
            if (contractItemInfo != null) {
                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", contractItemInfo);
            } else {
                result.put("code", 404);
                result.put("message", "合同项不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据合同ID查询合同项列表
     */
    @GetMapping("/contract/{contractId}")
    public JSONObject getContractItemsByContractId(@PathVariable String contractId) {
        JSONObject result = new JSONObject();
        try {
            List<ContractItemInfo> contractItems = contractItemInfoService.getByContractId(contractId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", contractItems);
            result.put("total", contractItems.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据支付订单号查询合同项信息
     */
    @GetMapping("/pay/{payNo}")
    public JSONObject getContractItemByPayNo(@PathVariable String payNo) {
        JSONObject result = new JSONObject();
        try {
            ContractItemInfo contractItemInfo = contractItemInfoService.getByPayNo(payNo);
            if (contractItemInfo != null) {
                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", contractItemInfo);
            } else {
                result.put("code", 404);
                result.put("message", "合同项不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据类型查询合同项列表
     */
    @GetMapping("/type/{type}")
    public JSONObject getContractItemsByType(@PathVariable Integer type) {
        JSONObject result = new JSONObject();
        try {
            List<ContractItemInfo> contractItems = contractItemInfoService.getByType(type);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", contractItems);
            result.put("total", contractItems.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据状态查询合同项列表
     */
    @GetMapping("/status/{status}")
    public JSONObject getContractItemsByStatus(@PathVariable Integer status) {
        JSONObject result = new JSONObject();
        try {
            List<ContractItemInfo> contractItems = contractItemInfoService.getByStatus(status);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", contractItems);
            result.put("total", contractItems.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有合同项信息
     */
    @GetMapping("/list")
    public JSONObject getAllContractItems() {
        JSONObject result = new JSONObject();
        try {
            List<ContractItemInfo> contractItems = contractItemInfoService.getAllContractItems();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", contractItems);
            result.put("total", contractItems.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 创建合同项信息
     */
    @PostMapping("/create")
    public JSONObject createContractItem(@RequestBody ContractItemInfo contractItemInfo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractItemInfoService.createContractItem(contractItemInfo);
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
     * 更新合同项信息
     */
    @PutMapping("/update")
    public JSONObject updateContractItem(@RequestBody ContractItemInfo contractItemInfo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractItemInfoService.updateContractItem(contractItemInfo);
            if (success) {
                result.put("code", 200);
                result.put("message", "更新成功");
            } else {
                result.put("code", 404);
                result.put("message", "合同项不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除合同项信息
     */
    @DeleteMapping("/{contractItemId}")
    public JSONObject deleteContractItem(@PathVariable String contractItemId) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractItemInfoService.deleteContractItem(contractItemId);
            if (success) {
                result.put("code", 200);
                result.put("message", "删除成功");
            } else {
                result.put("code", 404);
                result.put("message", "合同项不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新合同项状态
     */
    @PutMapping("/{contractItemId}/status/{status}")
    public JSONObject updateContractItemStatus(@PathVariable String contractItemId, @PathVariable Integer status) {
        JSONObject result = new JSONObject();
        try {
            boolean success = contractItemInfoService.updateStatus(contractItemId, status);
            if (success) {
                result.put("code", 200);
                result.put("message", "状态更新成功");
            } else {
                result.put("code", 404);
                result.put("message", "合同项不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "状态更新失败：" + e.getMessage());
        }
        return result;
    }
}