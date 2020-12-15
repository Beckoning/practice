package com.pattern.responsibilitychain;

public class SingleCardChain extends AbstractDkHandler{
    @Override
    public String invoke(String param) {
        System.out.println("SingleCardChain");
        return "SingleCardChain"+param;
    }
}
