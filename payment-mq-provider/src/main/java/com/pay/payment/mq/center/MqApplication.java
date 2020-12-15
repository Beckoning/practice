package com.pay.payment.mq.center;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = "com.pay")
@EnableDubbo(scanBasePackages = "com.pay")
public class MqApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqApplication.class);
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) throws Exception {


        Enumeration<URL> urls  =  Thread.currentThread().getContextClassLoader().getResources("META-INF/spring.factories");

        ctx = SpringApplication.run(MqApplication.class, args);

        LOGGER.info("spring.profiles.active:");
        for (String str : ctx.getEnvironment().getActiveProfiles()) {
            LOGGER.info(str);
        }

        LOGGER.info("Boot Server started.");
        new CountDownLatch(1).await();

    }

    public void destroy() throws Exception {
        if (null != ctx && ctx.isRunning()) {
            ctx.close();
        }
    }
}
