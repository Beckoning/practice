package com.pay.mq;

import com.pay.payment.mq.center.MqApplication;
import com.pay.payment.mq.center.mq.PushMessageProvider;
import com.pay.payment.mq.center.service.impl.SpringProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
@Slf4j
public class MqTest {


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private PushMessageProvider pushMessageProvider;

    @Test
    public void testSend() {


        String topic = "OPAY_TOPIC";
        SendResult sendResult = rocketMQTemplate.syncSend(topic,"ssss");
        log.info("[testSend] [syncSend] [发送完成] [{}]", sendResult);
        if(SendStatus.SEND_OK.equals(sendResult.getSendStatus())){
            System.out.println("sendSuccess");
        }
        System.out.println("sendFile");
    }

    @Autowired
    private SpringProducer springProducer;

    @Test
    public void testSendMessage() {
//        springProducer.sendMsg("my-topic","为什么不打印");


        pushMessageProvider.sendMessage("你好啊");
        System.out.println();
    }




}
