package com.pay.payment.center.classload;

/**
 * 通过VM参数判断操作是否会加载子类 -XX:+TraceClassLoading
 */
public class Initialization {


    public static void main(String[] args) {
//        test1();

//        test2();
        test3();
    }

    public static void test1() {
        //通过子类的引用父类中的静态字段，只会触发父类的初始化（静态代码块）而不会触发子类的初始化（静态代码块）
        System.out.println(SubClass.value);
    }

    public static void test2() {
        //使用数组的方式，不会触发初始化（触发了父类的加载，不会触发子类加载）
        SuperClass[] superClasses = new SuperClass[10];
    }

    public static void test3() {
        //打印一个常量，不会触发初始化（同样也不会触发类加载，编译的时候这个常量已经进入Class的常量池）
        System.out.println(SuperClass.HELLOW_WORD);
    }

    public static void test4() {
        //
        System.out.println(SuperClass.STATIC_VALUE);
    }


}
