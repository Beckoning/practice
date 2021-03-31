package com.pay.payment.center.springtest;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class TestPostConstruct {

    @Resource
    UserService userService;

    @PostConstruct
    public void tetPostConstruct(){

        System.out.println(userService.getUserById());
    }
}
