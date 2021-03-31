package com.pay.payment.center.suanfa.shuzu;

public class shuzubutongzhifu {



    public static String find(char[] chars1, char[] chars2) {

        if (chars1.length == 0 && chars2.length == 0) {
            return "";
        }
        if((chars1.length == 1 && chars2.length == 0) || chars1.length == 0 && chars2.length == 1){
            return chars1.length > chars2.length ? chars1[0]+"" : chars2[0]+"";
        }


        return findChar(chars1,chars2,0);
    }

    private static String findChar(char[] chars1, char[] chars2, int num) {

         int time = chars1.length > chars2.length ? chars1.length-1:chars2.length-1;
         if(time == num){
             return chars1.length > chars2.length ? chars1[num]+"" : chars2[num]+"";
         }

        if(chars1[num] != chars2[num]){
           if(chars1.length < chars2.length){
               return chars2[num]+"";
           }else{
               return chars1[num]+"";
           }
        }
        return findChar(chars1,chars2,num+1)+"";
    }

    public static void main(String[] args) {
        //测试1
//        char []c = {'a','b','c','d'};
//        char []c1 = {'a','b','c','d','e'};
        //测试2
//        char []c = {'a','b','c','e'};
//        char []c1 = {'a','b','c','d','e'};
        //测试3
//        char []c = {};
//        char []c1 = {'1'};
        //测试4
        char []c = {'2'};
        char []c1 = {'1','2'};
        System.out.println(find(c, c1));
    }

}
