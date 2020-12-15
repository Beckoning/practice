package com.pay.payment.center.resourcemap;

import java.util.Objects;

public abstract class AbstractDkHandler {


    private AbstractDkHandler next;


    public String process(String param) {


        System.out.println(invoke(param));
        if(Objects.nonNull(next)){
            next.process(param);
        }
        return "end";

    }

    public AbstractDkHandler getNext() {
        return next;
    }

    public void setNext(AbstractDkHandler next) {
        this.next = next;
    }

    public abstract String invoke(String param);



}
