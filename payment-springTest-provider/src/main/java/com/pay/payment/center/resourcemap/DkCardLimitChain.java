package com.pay.payment.center.resourcemap;

import org.springframework.stereotype.Component;

@Component
public class DkCardLimitChain extends AbstractDkHandler{
    @Override
    public String invoke(String param) {
        System.out.println("DkCardLimitChain");
        return "DkCardLimitChain"+param;
    }
}
