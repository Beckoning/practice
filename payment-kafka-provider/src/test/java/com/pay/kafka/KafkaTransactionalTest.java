package com.pay.kafka;

import com.pay.payment.kafka.provder.KafkaApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
@Slf4j
public class KafkaTransactionalTest {

    @Autowired
    @Qualifier("transactionalTemplate")
    KafkaTemplate transactionalTemplate;

    @Test
    public void testSend() throws Exception{

        try {
            transactionalTemplate.executeInTransaction(t->{
                t.send("transactionalkafka", 0,"23", "msg23");

//                t.flush();
                int a = 1/0;

                t.send("transactionalkafka", 0,"24", "msg24");
                return true;

            });


        } catch (Exception e) {
            log.error("发送kafka异常：", e);
        }


        new CountDownLatch(1).await();



    }

}
