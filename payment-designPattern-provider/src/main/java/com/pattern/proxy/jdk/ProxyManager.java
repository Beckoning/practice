package com.pattern.proxy.jdk;

public class ProxyManager {


    public static void main(String[] args) {

        JDKDynamicProxyHandler jdkDynamicProxyHandler = new JDKDynamicProxyHandler(new ChildPersion());
        Persion persion = jdkDynamicProxyHandler.getProxy();
        persion.say();
    }
}
