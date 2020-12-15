package com.pay.payment.center.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor  implements BeanPostProcessor {

    /**
     * 第七执行
     *
     * 在bean初始化（自定义init或者是实现了InitializingBean.afterPropertiesSet()）之前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if("x".equalsIgnoreCase(beanName)){
            System.out.println("x"+"postProcessBeforeInitialization");
        }
        return bean;
    }

    /**
     * 第八执行
     *
     * 在bean初始化（自定义init或者是实现了InitializingBean.afterPropertiesSet()）之后执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("x".equalsIgnoreCase(beanName)){
            System.out.println("x"+"postProcessAfterInitialization");
        }
        return bean;
    }
}
