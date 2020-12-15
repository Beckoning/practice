package com.pay.payment.mq.center.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
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
public class MqSimpleConsumer {

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;
    private DefaultMQPullConsumer defaultMQPullConsumer ;


    @PostConstruct
    public SendStatus simpleSend(String message) throws MQClientException {

        defaultMQPullConsumer = new DefaultMQPullConsumer();
        //设置机器
        defaultMQPullConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPullConsumer.start();

//        defaultMQPullConsumer.setMessageQueueListener();




        return null;
    }


    @PreDestroy
    public void destory(){

        if(Objects.nonNull(defaultMQPullConsumer)){
            defaultMQPullConsumer.shutdown();
        }
    }




}
