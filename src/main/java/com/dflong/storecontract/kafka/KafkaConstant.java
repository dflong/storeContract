package com.dflong.storecontract.kafka;

public class KafkaConstant {

    public static final String CONTRACT_STATUS = "2506_contract_status";

    public static final String CONTRACT_STATUS_GROUP_ID = "2506_contract_status_group";

    public static final String PAY_CALL = "2506_pay_call";

    public static final String PAY_CALL_GROUP_ID = "2506_pay_call_group";

    public static final int NUM_PARTITIONS = 4;

    public static final short REPLICATION_FACTOR = 3;

    public static final String DEAD_LETTER_TOPIC = "DEAD_LETTER_TOPIC";

}
