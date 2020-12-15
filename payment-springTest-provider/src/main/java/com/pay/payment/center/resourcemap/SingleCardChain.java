package com.pay.payment.center.resourcemap;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SingleCardChain extends AbstractDkHandler{
    @Override
    public String invoke(String param) {
        System.out.println("SingleCardChain");
        return "SingleCardChain"+param;
    }
}
