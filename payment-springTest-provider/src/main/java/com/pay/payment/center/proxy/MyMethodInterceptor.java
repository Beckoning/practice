package com.pay.payment.center.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {

//    private Object traget;
//
//    public MyMethodInterceptor(Object traget) {
//        this.traget = traget;
//    }

    /**
     * sub：cglib生成的代理对象
     * method：被代理对象方法
     * objects：方法入参
     * methodProxy: 代理方法
     */
    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("======插入前置通知======");
        Object object = methodProxy.invokeSuper(sub,objects);
        System.out.println("======插入后者通知======");
//        System.out.println("======插入前置通知======");
//        Object object =   method.invoke(traget,objects);
//        System.out.println("======插入后者通知======");

        return object;
    }

}
