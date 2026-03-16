package com.dflong.storecontract.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    // 默认幂等性
    public void send(String topic, String key, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic,  key, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("消息发送成功： " + result.getRecordMetadata().toString());
                // 删除发送mq定时任务
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("消息发送失败： ");
                // 默认开启幂等性，会自动重试
            }
        });
    }

    // 使用事务，在配置中配置trx-id
    public void sendWithTransaction(String topic, String key, String message) {
        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            ListenableFuture<SendResult<String, String>> future = kafkaOperations.send(topic,  key, message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("消息发送成功： " + result.getRecordMetadata().toString());
                }
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("消息发送失败： ");
                    // 默认开启幂等性，会自动重试
                }
            });
            return null;
        });
    }
}
