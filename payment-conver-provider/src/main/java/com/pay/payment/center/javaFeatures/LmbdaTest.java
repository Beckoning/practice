package com.pay.payment.center.javaFeatures;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LmbdaTest {

    public static void main(String[] args) {
//        Arrays.asList("2","4","535","we").forEach(a -> {
//            System.out.println(a);
//        });


        //lambda表达式使用
       List<String> list= Arrays.asList("21","24","2535","2we","234r","sd","df","fg");
//        System.out.println(JSON.toJSON(list));

//        Collections.sort(list,(a,b) -> b.compareTo(b));
//        Collections.sort(list, (a, b) -> {
//            int c = b.compareTo(a);
//            System.out.println(c);
//           return c;
//        });
//
//        System.out.println(JSON.toJSON(list));
//        Collections.sort(list,(a,));

//        List<String> ss = list.stream().filter(s -> s.startsWith("2")).map(String::toUpperCase) .collect(Collectors.toList());
//
//        List<String> ss1 = list.stream().filter(s -> s.startsWith("2")).map(String::toUpperCase) .collect(Collectors.toList());
//
//        System.out.println(JSON.toJSON(ss));

        Optional<String> reduced =
                list
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        System.out.println(reduced.get());


    }
}
