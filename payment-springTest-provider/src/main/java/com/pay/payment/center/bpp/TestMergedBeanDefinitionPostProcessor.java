//package com.pay.payment.center.bpp;
//
//import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {
//
//    /**
//     * 第三个被执行
//     *
//     * 在对象实例化完毕 初始化之前执行
//     * @param beanDefinition
//     * @param beanType
//     * @param beanName
//     */
//    @Override
//    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
//
//        if("x".equalsIgnoreCase(beanName)){
//            System.out.println("x"+"postProcessMergedBeanDefinition");
//        }
//    }
//
//    @Override
//    public void resetBeanDefinition(String beanName) {
//
//    }
//}
