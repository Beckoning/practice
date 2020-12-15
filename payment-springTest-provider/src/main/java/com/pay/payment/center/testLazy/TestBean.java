package com.pay.payment.center.testLazy;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/10/27.
 */
//@Component("testBean1")
//@Lazy
@Data
public class TestBean {
    private String name ;

    private String value;
    public TestBean() {
        System.out.println("TestBean has been initiated");
    }


    public TestBean(String name, String value) {
        System.out.println("TestBean has been initiated");

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
