package com.pay.payment.kafka.provder;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = "com.pay")
@EnableDubbo(scanBasePackages = "com.pay")
public class KafkaApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaApplication.class);
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) throws Exception {


        ctx = SpringApplication.run(KafkaApplication.class, args);

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

