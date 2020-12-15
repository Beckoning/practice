package com.pay.payment.mq.center.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * 普通的mq消息发送者
 *
 */
@Component
public class MqSimpleProvider {

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;
    private DefaultMQProducer defaultMQProducer ;


    @PostConstruct
    public SendStatus simpleSend(String message) throws MQClientException {

        defaultMQProducer = new DefaultMQProducer();
        //设置机器
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.start();

        Message msg  = new Message();

        msg.setKeys("key");
        msg.setBody("ooooo".getBytes());


        return null;
    }


    @PreDestroy
    public void destory(){

        if(Objects.nonNull(defaultMQProducer)){
            defaultMQProducer.shutdown();
        }
    }




}
