package com.pay.payment.center.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("------Before invocation");
        Object retVal = method.invoke(target, args);
        System.out.println("-----After invocation");
        return retVal;
    }
}
