package com.pay.payment.center.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class Y {
    @Autowired
    X x;

//    @Autowired
    public Y() {
        System.out.println("y"+"init");

    }
}
