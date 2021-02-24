package com.pay.payment.center.java8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LambdaTest {


    public static void main(String[] args) throws Exception{
        collectionsTest();

//        convertDate();
    }



    public static void collectionsTest(){
        List<String> valueList = new ArrayList<>();
        valueList.add("6");
        valueList.add("9");
        valueList.add("1");
        valueList.add("8");
        valueList.add("7");

        valueList.forEach(o3-> System.out.println(o3));

        System.out.println("----------------");

//        Collections.sort(valueList, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });

        valueList.sort((o1, o2) -> o1.compareTo(o2));
        valueList.forEach(o3-> System.out.println(o3));


    }


    public static void convertDate()throws Exception{
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//
//        System.out.println(formatter.parse("24-Sep-1990"));


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String dateInString = "7-Jun-2013";
        try {
            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
