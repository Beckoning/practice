package com.pay.kafka;

import com.pay.payment.kafka.provder.KafkaApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
@Slf4j
public class TestInvokeRemote {
    RestTemplate restTemplate = new RestTemplate();
    private static final int num = 1000;
    private final String url = "http://127.0.0.1:8090/buyTicket";
    // 倒计时器，用于模拟高并发
    private static CountDownLatch cdl = new CountDownLatch(num);



    @Test
    public void testInvokeRemote() throws InterruptedException {
        //模拟高并发
        for(int i = 0; i <num; i++){
            new Thread(new TicketQuest()).start();
            cdl.countDown(); //0, 所有线程同时起跑
        }
        Thread.currentThread().sleep(3000);
    }
 
    // 内部类继承线程接口，用于模拟用户买票请求
    public class TicketQuest implements Runnable{
        @Override
        public void run() {
            try {
                cdl.await();//在起跑线等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String str = restTemplate.getForEntity(url, String.class).getBody();
            System.out.println(str);
        }
    }
}