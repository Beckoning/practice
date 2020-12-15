package com.pay.payment.center.javaFeatures;

import com.alibaba.fastjson.JSON;
import com.pay.payment.center.factoryBean.CustomTimerTask;

public class MainTest {

    public static void main(String[] args) {
        
        lambdaFunction();
    }

    private static void lambdaFunction() {
        //函数式接口
//        Converter<String, Integer> converter =from -> Integer.valueOf(from);
//        Integer converted = converter.convert("123");
//        System.out.println(converted);    // 123



        //converter 就是一个执行方法
//        Converter<String, Integer> converter1 =from ->{
//
//            if(from instanceof String){
//                System.out.println("i am a zimu");
//            }else{
//                System.out.println("i am a shuzi");
//            }
//
//            return -1;
//        };
//        System.out.println(converter1.convert("121212"));


//        Converter<String, Integer> converter = Integer::parseInt;
//        Integer converted = converter.convert("123");
//        System.out.println(converted);   // 123

        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
        System.out.println(JSON.toJSON(person));

    }


}
