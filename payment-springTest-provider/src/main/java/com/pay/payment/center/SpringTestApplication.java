package com.pay.payment.center;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.pay.payment.center.springtest.ResourceMapTest;
import com.pay.payment.center.springtest.UserService;
import com.pay.payment.center.springtest.X;
import com.pay.payment.center.springtest.Y;
import com.pay.payment.center.testLazy.TestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = "com.pay")
@EnableDubbo(scanBasePackages = "com.pay")
//@EnableAspectJAutoProxy
public class SpringTestApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringTestApplication.class);
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) throws Exception {


        args = new String[]{"44"};
        ctx = SpringApplication.run(SpringTestApplication.class, args);

        LOGGER.info("spring.profiles.active:");
        for (String str : ctx.getEnvironment().getActiveProfiles()) {
            LOGGER.info(str);
        }


//        UserService userService =  ctx.getBean(UserService.class);
//
//        userService.getUserById();
//        TestBean testBean =  (TestBean)ctx.getBean("testBean2");


        LOGGER.info("Boot Server started.");
        new CountDownLatch(1).await();


    }

    public void destroy() throws Exception {
        if (null != ctx && ctx.isRunning()) {
            ctx.close();
        }
    }
}
