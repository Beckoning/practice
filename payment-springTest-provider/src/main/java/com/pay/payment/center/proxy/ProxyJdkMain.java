package com.pay.payment.center.proxy;

import com.pay.payment.center.proxy.entity.HelloWorld;
import com.pay.payment.center.proxy.entity.HelloWorldImpl;

import java.lang.reflect.Proxy;


public class ProxyJdkMain {

    public static void main(String[] args) {


        HelloWorldImpl helloWorld = new HelloWorldImpl();
        MyInvocationHandler handler = new MyInvocationHandler(helloWorld);

        HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(
                ProxyJdkMain.class.getClassLoader(),
                new Class[]{HelloWorld.class},
                handler);
        proxy.sayHello("Mikan");
    }
}
