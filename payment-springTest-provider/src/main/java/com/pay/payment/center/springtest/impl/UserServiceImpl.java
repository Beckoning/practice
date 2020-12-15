package com.pay.payment.center.springtest.impl;

import com.pay.payment.center.springtest.UserService;
//import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
//@Scope("prototype")
//@Lazy
public class UserServiceImpl implements UserService {

    @Override
    public void getUserById() {

        System.out.println("id");
    }
}
