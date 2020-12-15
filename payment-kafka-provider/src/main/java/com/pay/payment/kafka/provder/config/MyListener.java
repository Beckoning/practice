package com.pay.payment.kafka.provder.config;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class MyListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @KafkaListener(topics = {"order"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("收到消息的key: " + record.key());
        logger.info("收到消息的value: " + record.value().toString());
    }
}