package com.dflong.storecontract.controller;

import com.dflong.storecontract.entity.PayInfo;
import com.dflong.storecontract.service.PayInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付信息控制器
 */
@RestController
@RequestMapping("/api/pay")
public class PayInfoController {

    @Autowired
    private PayInfoService payInfoService;

    /**
     * 根据支付订单号查询支付信息
     */
    @GetMapping("/{payNo}")
    public JSONObject getPayInfoByPayNo(@PathVariable String payNo) {
        JSONObject result = new JSONObject();
        try {
            PayInfo payInfo = payInfoService.getByPayNo(payNo);
            if (payInfo != null) {
                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", payInfo);
            } else {
                result.put("code", 404);
                result.put("message", "支付信息不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据合同ID查询支付列表
     */
    @GetMapping("/contract/{contractId}")
    public JSONObject getPayInfoByContractId(@PathVariable String contractId) {
        JSONObject result = new JSONObject();
        try {
            List<PayInfo> payInfos = payInfoService.getByContractId(contractId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", payInfos);
            result.put("total", payInfos.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据合同项ID查询支付信息
     */
    @GetMapping("/contract-item/{contractItemId}")
    public JSONObject getPayInfoByContractItemId(@PathVariable String contractItemId) {
        JSONObject result = new JSONObject();
        try {
            PayInfo payInfo = payInfoService.getByContractItemId(contractItemId);
            if (payInfo != null) {
                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", payInfo);
            } else {
                result.put("code", 404);
                result.put("message", "支付信息不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据支付状态查询支付列表
     */
    @GetMapping("/status/{status}")
    public JSONObject getPayInfoByStatus(@PathVariable Integer status) {
        JSONObject result = new JSONObject();
        try {
            List<PayInfo> payInfos = payInfoService.getByStatus(status);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", payInfos);
            result.put("total", payInfos.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有支付信息
     */
    @GetMapping("/list")
    public JSONObject getAllPayInfo() {
        JSONObject result = new JSONObject();
        try {
            List<PayInfo> payInfos = payInfoService.getAllPayInfo();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", payInfos);
            result.put("total", payInfos.size());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 创建支付信息
     */
    @PostMapping("/create")
    public JSONObject createPayInfo(@RequestBody PayInfo payInfo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = payInfoService.createPayInfo(payInfo);
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
     * 更新支付信息
     */
    @PutMapping("/update")
    public JSONObject updatePayInfo(@RequestBody PayInfo payInfo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = payInfoService.updatePayInfo(payInfo);
            if (success) {
                result.put("code", 200);
                result.put("message", "更新成功");
            } else {
                result.put("code", 404);
                result.put("message", "支付信息不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除支付信息
     */
    @DeleteMapping("/{payNo}")
    public JSONObject deletePayInfo(@PathVariable String payNo) {
        JSONObject result = new JSONObject();
        try {
            boolean success = payInfoService.deletePayInfo(payNo);
            if (success) {
                result.put("code", 200);
                result.put("message", "删除成功");
            } else {
                result.put("code", 404);
                result.put("message", "支付信息不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新支付状态
     */
    @PutMapping("/{payNo}/status/{status}")
    public JSONObject updatePayStatus(@PathVariable String payNo, @PathVariable Integer status) {
        JSONObject result = new JSONObject();
        try {
            boolean success = payInfoService.updateStatus(payNo, status);
            if (success) {
                result.put("code", 200);
                result.put("message", "状态更新成功");
            } else {
                result.put("code", 404);
                result.put("message", "支付信息不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "状态更新失败：" + e.getMessage());
        }
        return result;
    }
}