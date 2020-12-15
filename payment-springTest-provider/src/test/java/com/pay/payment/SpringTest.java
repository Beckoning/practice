package com.pay.payment;

import com.pay.payment.center.SpringTestApplication;
import com.pay.payment.center.springtest.ResourceMapTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringTestApplication.class)
@Slf4j
public class SpringTest {

    @Resource
    ResourceMapTest resourceMapTest;

    @Test
    public void testMap(){
        resourceMapTest.testMap();
    }
}
