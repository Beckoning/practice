package com.pay.payment.center.proxy;

import com.pay.payment.center.proxy.MyMethodInterceptor;
import com.pay.payment.center.proxy.entity.HelloWorld;
import com.pay.payment.center.proxy.entity.HelloWorldImpl;
import org.springframework.cglib.proxy.Enhancer;

public class ProxyCglibMain {

    public static void main(String[] args) {
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
        // 通过CGLIB动态代理获取代理对象的过程
        HelloWorld helloWorld = new HelloWorldImpl();
        Enhancer enhancer = new Enhancer();
//        // 设置enhancer对象的父类
        enhancer.setSuperclass(HelloWorldImpl.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new MyMethodInterceptor());
        // 创建代理对象
        HelloWorld proxy= (HelloWorld)enhancer.create();
        // 通过代理对象调用目标方法
        proxy.sayHello("nameTest");
    }


}
