package com.pay.payment.mq.center.constant;

public interface Constant {

    /**
     * PushMessage 消息主题
     */
    String PUSH_MESSAGE_TOPIC = "PAY_PUSH_MESSAGE_NOTICE_NOTIFY";

    String PUSH_MESSAGE_GROUP = "PAY_PUSH_MESSAGE_NOTICE_GROUP";


    /**
     * 测试Mq事务 消费主题
     */
    String TRANSACTIONAL_TOPIC = "PAY_TRANSACTIONAL_TOPIC_NOTICE_NOTIFY";

    String TRANSACTIONAL_GROUP= "PAY_TRANSACTIONAL_TOPIC_NOTICE_GROUP";



}
