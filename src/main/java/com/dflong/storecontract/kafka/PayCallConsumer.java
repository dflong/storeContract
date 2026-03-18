package com.dflong.storecontract.kafka;

import com.dflong.storeapi.api.PayStatus;
import com.dflong.storecontract.constant.TaskJobType;
import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.entity.LogInfo;
import com.dflong.storecontract.mapper.LogInfoMapper;
import com.dflong.storecontract.mapper.PayInfoMapper;
import com.dflong.storecontract.mapper.TaskJobMapper;
import com.dflong.storecontract.rest.ContractQueryService;
import com.dflong.storecontract.rpc.CouponRpcServiceRpc;
import com.dflong.storecontract.transaction.ContractTransaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class PayCallConsumer {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    CouponRpcServiceRpc couponRpcServiceRpc;

    @Autowired
    ContractQueryService contractQueryService;

    @Autowired
    LogInfoMapper logInfoMapper;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    PayInfoMapper payInfoMapper;

    @Autowired
    ContractTransaction contractTransaction;

    @Autowired
    private TaskJobMapper taskJobMapper;

    @KafkaListener(id = "2506:pay:call", topics = KafkaConstant.PAY_CALL, groupId = KafkaConstant.PAY_CALL_GROUP_ID)
    public void receivePayCall(List<ConsumerRecord<String, String>> records,
                        Acknowledgment ack
//                        @Header("kafka_receivedTopic") String topic
//                        @Header("kafka_receivedPartitionId") int partition,
//                        @Header(KafkaHeaders.OFFSET) long offset
    ) {
        logger.info(KafkaConstant.PAY_CALL + " receive records num: " + records.size());
        for (ConsumerRecord<String, String> record : records) {
            ContractInfo contractInfo = contractQueryService.getByContractId(record.key());
            try {
                // todo 冻结优惠券、套餐、会员卡、埋点等。所有操作都是先往任务表插入一条数据，执行成功后删除，否则定时任务扫表执行
                contractTransaction.paySuccess(contractInfo);
            } catch (Exception e) {
                // 如果这里失败，则需要重新消费
                logger.info("kafka消费者监听消息，消费失败： 订单号: " + record.key() + ", 订单信息: " + record.value());
                continue;
            }

            try {
                payCall(contractInfo);
                logger.info("kafka消费者监听消息： 订单号: " + record.key() + ", 订单信息: " + record.value());
            } catch (Exception e) {
                // 这里失败，让job重新扫表执行
            } finally {
                // 手动ack, 通知kafka已经消费
                ack.acknowledge();
            }
        }
    }

    public void payCall( ContractInfo contractInfo) {
        String contractId = contractInfo.getContractId();
        try {
            payInfoMapper.updateStatus(contractId, PayStatus.PAYED.getCode(), PayStatus.WAIT_PAY.getCode());
            if (contractInfo.getCouponId() > 0) {
                boolean freeze = couponRpcServiceRpc.freeze(contractInfo.getCouponId());
                if (freeze) {
                    taskJobMapper.deleteByTaskIdAndTaskType(contractInfo.getCouponId() + "", TaskJobType.FREEZE_COUPON);
                }
            }
            if (contractInfo.getPackageId() > 0) {
//            packageRpcServiceRpc.freeze(contractInfo.getPackageId());
            }
            createLog(contractId, "下单支付成功", 2);
            // 支付成功埋点
        } catch (Exception e) {
            return; // 让定时任务重试
        }
    }

    private void createLog(String contractId, String name, int type) {
        LogInfo logInfo = new LogInfo();
        logInfo.setType(type);
        logInfo.setName(name);
        logInfo.setDesc(contractId);
        logInfoMapper.insert(logInfo);
    }
}
