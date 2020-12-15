package com.pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxyHandler implements InvocationHandler {


    private Persion persion;

    public JDKDynamicProxyHandler(Persion persion) {
        this.persion = persion;
    }

    public Persion getProxy() {

        return (Persion) Proxy.newProxyInstance(persion.getClass().getClassLoader(),persion.getClass().getInterfaces(),this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始");
        Object result  = method.invoke(persion,args);
        System.out.println("结束");
        return result;
    }
}
