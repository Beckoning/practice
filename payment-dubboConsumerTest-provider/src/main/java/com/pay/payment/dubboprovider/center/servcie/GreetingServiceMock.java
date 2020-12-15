package com.pay.payment.dubboprovider.center.servcie;

import com.pay.payment.dubboprovider.center.common.PoJo;
import com.pay.payment.dubboprovider.center.common.Result;

public class GreetingServiceMock implements GreetingService{
    @Override
    public String sayHello(String name) {
        return "mock value";
    }


    @Override
    public String sayHello() {
        return "mock value";
    }

    @Override
    public Result<String> testGeneric(PoJo poJo) {
        return null;
    }
}
