package com.pay.payment.center.springtest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
@Data
public class X {
    String name;
    @Autowired
    Y y;

    public X() {
        System.out.println("x"+"init");
    }

//    @Autowired
//    public X(String name) {
//        this.name = name;
//        System.out.println("x"+"init");
//    }



//    @Autowired
//    public X(Y y){
//        this.y = y;
//    }


    public X(String name) {
        this.name = name;
        System.out.println("x"+"init"+name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
