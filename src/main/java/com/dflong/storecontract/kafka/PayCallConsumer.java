package com.dflong.storecontract.kafka;

import com.dflong.storeapi.api.PayStatus;
import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.entity.LogInfo;
import com.dflong.storecontract.mapper.LogInfoMapper;
import com.dflong.storecontract.mapper.PayInfoMapper;
import com.dflong.storecontract.rest.ContractQueryService;
import com.dflong.storecontract.rpc.CouponRpcServiceRpc;
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

    @KafkaListener(id = "2506:pay:call", topics = KafkaConstant.PAY_CALL, groupId = KafkaConstant.PAY_CALL_GROUP_ID)
    public void receivePayCall(List<ConsumerRecord<String, String>> records,
                        Acknowledgment ack
//                        @Header("kafka_receivedTopic") String topic
//                        @Header("kafka_receivedPartitionId") int partition,
//                        @Header(KafkaHeaders.OFFSET) long offset
    ) {
        logger.info(KafkaConstant.PAY_CALL + " receive records num: " + records.size());
        for (ConsumerRecord<String, String> record : records) {
            try {
                // todo 冻结优惠券、套餐、会员卡、埋点等。所有操作都是先往任务表插入一条数据，执行成功后删除，否则定时任务扫表执行
                payCall(record.key());
                logger.info("kafka消费者监听消息： 订单号: " + record.key() + ", 订单信息: " + record.value());
            } catch (Exception e) {
                // 如果失败，写入数据库重试或者发送到另一个retry topic
            } finally {
                // 手动ack, 通知kafka已经消费
                ack.acknowledge();
            }
        }
    }

    private void payCall(String contractId) {
        createLog(contractId, "下单支付成功", 2);
        payInfoMapper.updateStatus(contractId, PayStatus.PAYED.getCode(), PayStatus.WAIT_PAY.getCode());
        ContractInfo contractInfo = contractQueryService.getByContractId(contractId);
        if (contractInfo.getCouponId() > 0) {
            couponRpcServiceRpc.freeze(contractInfo.getCouponId());
        }
        if (contractInfo.getPackageId() > 0) {
//            packageRpcServiceRpc.freeze(contractInfo.getPackageId());
        }
        // 支付成功埋点
    }

    private void createLog(String contractId, String name, int type) {
        LogInfo logInfo = new LogInfo();
        logInfo.setType(type);
        logInfo.setName(name);
        logInfo.setDesc(contractId);
        logInfoMapper.insert(logInfo);
    }

}
