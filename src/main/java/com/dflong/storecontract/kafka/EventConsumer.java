package com.dflong.storecontract.kafka;

import com.dflong.storecontract.entity.ContractInfo;
import com.dflong.storecontract.rest.ContractQueryService;
import com.dflong.storecontract.rpc.provider.CouponRpcServiceRpc;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class EventConsumer {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private CouponRpcServiceRpc couponRpcServiceRpc;

    @Autowired
    ContractQueryService contractQueryService;

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
                ContractInfo contractInfo = contractQueryService.getByContractId(record.key());
                couponRpcServiceRpc.freeze(contractInfo.getCouponId());
                // todo 冻结优惠券、套餐、会员卡、埋点等。所有操作都是先往任务表插入一条数据，执行成功后删除，否则定时任务扫表执行
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
                logger.info("kafka消费者监听消息： 订单号: " + record.key() + ", 订单信息: " + record.value());
            } catch (Exception e) {
                // 如果失败，写入数据库重试或者发送到另一个retry topic
            } finally {
                // 手动ack, 通知kafka已经消费
                ack.acknowledge();
            }
        }
    }

}
