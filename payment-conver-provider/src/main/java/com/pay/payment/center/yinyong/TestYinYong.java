package com.pay.payment.center.yinyong;


/**
 * 测试引用传递
 */
public class TestYinYong {


    public static void main(String[] args) {

//        Person person = new Person("22");
//        System.out.println(person.getName());
//        printA(person);
//        System.out.println(person.getName());
//        int num = 1;
//        System.out.println(num);
//        printB(num);
//        System.out.println(num);

        String a = new String("kin").intern();
        String b = new String("kin").intern();
        String c = "kin";
        String e = new String("kin");


        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a == e);






    }

    public static void printA(Person person) {
        person = new Person("444");
        person.setName("333");

    }

    public static void printB(int num) {
        num ++;
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
