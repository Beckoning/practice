package com.pay.payment.center.proxy.entity;

public class HelloWorldImpl implements HelloWorld {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }
}
