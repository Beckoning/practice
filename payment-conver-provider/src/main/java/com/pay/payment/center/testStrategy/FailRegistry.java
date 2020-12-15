package com.pay.payment.center.testStrategy;

public abstract class FailRegistry implements Registry {


    @Override
    public void reigstry(String name) {
        System.out.println(name +" com.pay.payment.center.testStrategy.FailRegistry");
        doRegistry(name);
    }

    public abstract void doRegistry(String name);
}
