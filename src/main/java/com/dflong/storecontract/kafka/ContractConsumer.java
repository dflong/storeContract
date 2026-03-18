package com.dflong.storecontract.kafka;

import com.dflong.storecontract.entity.LogInfo;
import com.dflong.storecontract.mapper.LogInfoMapper;
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
public class ContractConsumer {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    CouponRpcServiceRpc couponRpcServiceRpc;

    @Autowired
    ContractQueryService contractQueryService;

    @Autowired
    LogInfoMapper logInfoMapper;

    @Autowired
    EventProducer eventProducer;

    @KafkaListener(id = "2506:contract:status", topics = KafkaConstant.CONTRACT_STATUS, groupId = KafkaConstant.CONTRACT_STATUS_GROUP_ID)
    public void receiveContractStatus(List<ConsumerRecord<String, String>> records,
                        Acknowledgment ack
//                        @Header("kafka_receivedTopic") String topic
//                        @Header("kafka_receivedPartitionId") int partition,
//                        @Header(KafkaHeaders.OFFSET) long offset
    ) {
        logger.info(KafkaConstant.CONTRACT_STATUS + " receive records num: " + records.size());
        for (ConsumerRecord<String, String> record : records) {
            try {
                createLog(record.key(), "创建租车订单", 1);
                // 模拟支付回调
                eventProducer.send(KafkaConstant.PAY_CALL, record.key(), record.value());
                logger.info("kafka消费者监听消息： 订单号: " + record.key() + ", 订单信息: " + record.value());
            } catch (Exception e) {
                logger.info("kafka消费者监听消息，消费失败： 订单号: " + record.key() + ", 订单信息: " + record.value());
                // 如果失败，写入数据库重试或者发送到另一个retry topic
            } finally {
                // 手动ack, 通知kafka已经消费
                ack.acknowledge();
            }
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
