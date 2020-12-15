package com.pay.payment.center.testStrategy;


public class DubboTestRegistry extends FailRegistry {

    DubboTestRegistry dubboTestRegistry = new DubboTestRegistry();
    @Override
    public void doRegistry(String name) {
        System.out.println(name+ "com.pay.payment.center.testStrategy.DubboTestRegistry");

        dubboTestRegistry.reigstry(name);
    }


    public static void main(String[] args) {

        DubboTestRegistry dubboTestRegistry = new DubboTestRegistry();

        dubboTestRegistry.doRegistry("12345678");

    }
}
