//package com.pay.payment.center.bpp;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {
//
//    /**
//     * 第九执行
//     * postProcessBeforeDestruction(Object bean, String beanName)会在销毁对象前执行
//     * @param bean
//     * @param beanName
//     * @throws BeansException
//     */
//    @Override
//    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
//        if("x".equalsIgnoreCase(beanName)){
//            System.out.println("x"+"postProcessBeforeDestruction");
//        }
//    }
//
//    @Override
//    public boolean requiresDestruction(Object bean) {
//        return false;
//    }
//}
