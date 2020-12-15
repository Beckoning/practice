package com.pay.payment.center.factoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    @Bean
    public CustomFactoryBean customTask() {
        CustomFactoryBean taskFactoryBean = new CustomFactoryBean();
        taskFactoryBean.setCorn("0 15 10 * * ?");
        String word = "定时任务一";
        ProcessorService someService = new ProcessorService();
        taskFactoryBean.setProcessorService(someService);
        return taskFactoryBean;
    }

    @Bean
    public CustomFactoryBean otherTask() {
        CustomFactoryBean taskFactoryBean = new CustomFactoryBean();
        taskFactoryBean.setCorn("0 15 17 * * ?");
        String word = "定时任务二";
        ProcessorService someService = new ProcessorService();
        taskFactoryBean.setProcessorService(someService);
        return taskFactoryBean;
    }


    @Bean
    public CustomTimerTask nonFactoryBeanTask() {
        CustomTimerTask customFactoryBean = new CustomTimerTask();
        customFactoryBean.setCorn("0 15 20 * * ?");
        String word = "定时任务三";
        ProcessorService someService = new ProcessorService();
        customFactoryBean.setProcessorService(someService);
        return customFactoryBean;
    }

}
