package com.pay.payment.center.suanfa.lianbiao;

import java.util.Scanner;

import java.util.Stack;


/**
 * 字符串反转
 */
public class zifuchuanfanzhuan {

    public static void main(String[] args) {



        String ss = "123456789";
        System.out.println(reverse5(ss));
    }

 

    //利用StringBuffer的内置reverse方法进行逆序排序

    public static String reverse1(String str) {

        return new StringBuffer(str).reverse().toString();

    }

 

    //从头部开始,正序:通过字符串数组实现从尾部开始esrever顺序逐个进入字符串reverse

    public static String reverse2(String str) {

        String reverse = "";

        for (int i = 0; i < str.length(); i++) {

            reverse = str.charAt(i) + reverse;

        }
        return reverse;
    }

 

    //从尾部开始,倒序(O(n))
    public static String reverse3(String str) {

        char[] arr = str.toCharArray();//String 转换成char数组

        String reverse = "";

        for (int i = arr.length - 1; i >= 0; i--) {

            reverse += arr[i];

        }
        return reverse;

    }

 

    //利用栈:First In Last Out(O(n))
    public static String reverse4(String str) {

        StringBuffer sb = new StringBuffer();

        Stack<Character> s = new Stack<Character>();//创建只装字符型的stack

        for (int i = 0; i < str.length(); i++) {
            s.add(str.charAt(i));
        }
        for (int i = 0; i < str.length(); i++) {
            sb.append(s.pop());//出栈，StringBuffer.append()添加到sb的缓冲区末端；.insert(int index,添加的内容)添加到指定位
        }
        return sb.toString();

    }

 

    //递归 log(n)
    public static String reverse5(String str){


        int len = str.length();
        if(len<=1){
            return str;
        }

        String left = str.substring(0,len/2);
        String right = str.substring(len/2,len);
        return reverse5(right)+reverse5(left);

//      int len=str.length();
//
//      if(len<=1)return str;
//
//      String left=str.substring(0,len/2);
//
//      String right=str.substring(len/2,len);
//
//      return reverse5(right)+reverse5(left);

    }

 

}

 