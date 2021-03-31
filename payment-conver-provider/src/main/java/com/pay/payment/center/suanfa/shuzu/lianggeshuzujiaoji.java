package com.pay.payment.center.suanfa.shuzu;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * 两个有序 集合查找交集
 */
class lianggeshuzujiaoji {


    public static  int[] retainAll(int[] array1, int[] array2) {
        if (array1.length == 0 || array2.length == 0) {
            return new int[0];
        }

        boolean[] hash = new boolean[1000];
        for (int a1 : array1) {
            hash[a1] = true;
        }

        int count = 0;
        int[] intersection = new int[Math.min(array1.length, array2.length)];
        for (int a2 : array2) {
            if (hash[a2]) {
                intersection[count++] = a2;
                hash[a2] = false;
            }
        }

        return Arrays.copyOf(intersection, count);
    }

    public static void main(String[] args) {
        //测试1
//        int num[] ={1,2,3,4,5};
//        int num1 [] ={3,4,5,6,7,8};
        //测试 2
//        int num[] ={};
//        int num1 [] ={3,4,5,6,7,8};
//        //测试3
//        int num[] ={};
//        int num1 [] ={};
//        System.out.println(JSON.toJSON(retainAll(num, num1)));



        String ss = "xyzabcabqab";

        String ll =  "ab";


        char[] ssChars = ss.toCharArray();
        int ssLen  = ss.toCharArray().length;

        int llLen = ll.toCharArray().length;

        int num = 0;
        for(int i=0;i<ssChars.length;i++){

            if(ll.startsWith(ssChars[i]+"") && ssLen>= llLen+i ){
                if(ll.equals(ss.substring(i,i+llLen))){
                    System.out.println("位置"+(i+1));
                }
            }
        }

    }
}
