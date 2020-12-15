package com.pattern.responsibilitychain;

public class SingleDayChain extends AbstractDkHandler {
    @Override
    public String invoke(String param) {
        System.out.println("SingleDayChain");
        return "SingleDayChain"+param;
    }
}
