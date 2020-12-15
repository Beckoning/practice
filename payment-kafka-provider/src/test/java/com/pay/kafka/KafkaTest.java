package com.pay.kafka;

import com.pay.payment.kafka.provder.KafkaApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
@Slf4j
public class KafkaTest {


    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testSend() {

        try {
            log.info("kafka的消息={}");
            String msg = "84304";
            ListenableFuture  listenableFuture =  kafkaTemplate.send("order", 0,"23", msg);
            listenableFuture.addCallback(o -> System.out.println("send-消息发送成功：" + msg), throwable -> System.out.println("消息发送失败：" + msg));
//            kafkaTemplate.flush();

            new CountDownLatch(1).await();

        } catch (Exception e) {
            log.error("发送kafka异常：", e);
        }



    }


    @Test
    public void testSendAck(){
        try {
            log.info("kafka的消息={}");
            kafkaTemplate.send("testAck", "keytestSendAck", "testSendAckValue");
        } catch (Exception e) {
            log.error("发送kafka异常：", e);
        }
    }

}
