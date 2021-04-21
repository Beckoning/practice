package com.pay.payment.center.yinyong;


/**
 * 测试引用传递
 */
public class TestYinYong {


    public static void main(String[] args) {

        Person person = new Person("22");
        System.out.println(person.getName());
        printA(person);
        System.out.println(person.getName());


    }

    public static void printA(Person person) {
        person = new Person("444");
        person.setName("333");

    }


}



class Person{

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
