package com.pay.payment.center.classload;

public class SuperClass {

    public static final String HELLOW_WORD = "HELLOW_WORD";
    public static String value = "12";
    public static final String STATIC_VALUE = value;

    static {
        System.out.println("SuperClass init");
    }
}
