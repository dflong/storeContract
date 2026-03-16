package com.dflong.storecontract.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic contractStatus() {
        Map<Integer, List<Integer>> replicasAssignments; // 可以指定分区、副本存放位置
        return new NewTopic(KafkaConstant.CONTRACT_STATUS, KafkaConstant.NUM_PARTITIONS, KafkaConstant.REPLICATION_FACTOR);
    }

    @Bean
    public NewTopic payCall() {
        Map<Integer, List<Integer>> replicasAssignments; // 可以指定分区、副本存放位置
        return new NewTopic(KafkaConstant.PAY_CALL, KafkaConstant.NUM_PARTITIONS, KafkaConstant.REPLICATION_FACTOR);
    }

    @Bean
    public NewTopic deadLetterTopic() {
        return new NewTopic(KafkaConstant.DEAD_LETTER_TOPIC, 1, (short) 1);
    }


}
