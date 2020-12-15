package com.pay.payment.dubboprovider.center;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = "com.pay")
@EnableDubbo(scanBasePackages = "com.pay")
public class DubbleProviderTestApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubbleProviderTestApplication.class);
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) throws Exception {

        ctx = SpringApplication.run(DubbleProviderTestApplication.class, args);

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
