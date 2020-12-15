package com.pay.payment.center.resourcemap;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SingleDayChain extends AbstractDkHandler {
    @Override
    public String invoke(String param) {
        System.out.println("SingleDayChain");
        return "SingleDayChain"+param;
    }

}
