//package com.pay.payment.center.bpp;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.PropertyValues;
//import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//
///**
// *
//
// */
//@Component
//public class TestInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
//
//
//
//    /**
//     * 第一个被执行的方法
//     *
//     * bean实例化之前被调用
//     *  如果有返回实例则直接使用，不会去走下面创建对象的逻辑，并在之后执行 BeanPostProcessor.postProcessAfterInitialization(result, beanName) 此时创建bean方法结束
//     *  如果没有返回bean创建实例 会往后执行
//     *
//     *
//     *  在目标对象实例化之前调用，方法的返回值
//     *
//     * @param beanClass
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
//        if("x".equalsIgnoreCase(beanName)){
//            System.out.println("x"+"postProcessBeforeInstantiation");
//        }
//        return null;
//    }
//
//    /**
//     * 第五个被执行
//     *
//     * 在bean创建完毕初始化之前执行
//     * @param bean
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
//
//        if("x".equalsIgnoreCase(beanName)){
//            System.out.println("x"+"postProcessAfterInstantiation");
//        }
//        return true;
//    }
//
//    /**
//     * 第 六次个执行
//     *
//     * 在bean的property属性注入完毕 向bean中设置属性之前执行
//     * @param pvs
//     * @param bean
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
//        if("x".equalsIgnoreCase(beanName)){
//            System.out.println("x"+"postProcessProperties");
//        }
//        return null;
//    }
//
//
//}
