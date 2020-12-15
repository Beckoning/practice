package com.pay.payment.center.dealMethod;

public class ClassProperties {

    /**
     * 类属性（静态变量）
     */
    public static  String name = "2323";

    /**
     * 成员变量（）
     */
    public String value = "2323";

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ClassProperties.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        ClassProperties classProperties1 = new ClassProperties();
        System.out.println("value = " +classProperties1.getValue() +"\n" +"name = " +classProperties1.getName());
        ClassProperties classProperties2 = new ClassProperties();
        System.out.println("value = " +classProperties2.getValue() +"\n" +"name = " +classProperties2.getName());

        classProperties1.setValue("4343");
        classProperties1.setName("4343");

        System.out.println("classProperties1>>>>>>>>>"+"value = " +classProperties1.getValue() +"\n" +"name = " +classProperties1.getName());
        System.out.println("classProperties2>>>>>>>"+"value = " +classProperties2.getValue() +"\n" +"name = " +classProperties2.getName());



    }
}
