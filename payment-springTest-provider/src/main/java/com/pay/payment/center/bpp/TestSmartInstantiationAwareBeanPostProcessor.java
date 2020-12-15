//package com.pay.payment.center.bpp;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Constructor;
//
//@Component
//public class TestSmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {
//
//    /**
//     * 是用来预判类型的
//     *
//     * @param beanClass
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
//        return null;
//    }
//
//    /**
//     * 第二个被执行
//     *
//     * 如果需要的话 会在实例化对象之前执行
//     *
//     * @param beanClass
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
//
//        if("x".equalsIgnoreCase(beanName)){
//            System.out.println("x"+"determineCandidateConstructors");
//        }
//        return new Constructor[0];
//    }
//
//    /**
//     * 第四次执行
//     *  用户生成bean的代理对象
//     * @param bean
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//}
