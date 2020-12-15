package com.pay.payment.center.testLazy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
 
/**
 * Created by Administrator on 2019/10/27.
 */
@Configuration
//@ComponentScan("com.spring.ioc")
public class TestConfiguration {

    @Value("${server.port}")
    private static Integer port;


    @Bean("testBean2")
    @Lazy()
    public TestBean testBean() {
        return new TestBean(port+"",port+"");
    }
}
