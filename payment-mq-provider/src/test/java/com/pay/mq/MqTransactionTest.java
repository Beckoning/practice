package com.pay.mq;

import com.pay.payment.mq.center.MqApplication;
import com.pay.payment.mq.center.mq.MqTransactionalProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
@Slf4j
public class MqTransactionTest {

    @Resource
    MqTransactionalProvider mqTransactionalProvider;

    @Test
    public void testSend() throws Exception{
        mqTransactionalProvider.send();

        new CountDownLatch(1).await();
    }
}
