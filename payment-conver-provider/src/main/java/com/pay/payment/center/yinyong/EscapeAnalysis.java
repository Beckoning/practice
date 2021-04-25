package com.pay.payment.center.yinyong;


//-XX:+DoEscapeAnalysis ： 表示开启逃逸分析  从jdk 1.7开始已经默认开始逃逸分析
//-XX:-DoEscapeAnalysis 表示关闭逃逸分析
//-Xmx20m  -XX:+PrintGC
public class EscapeAnalysis {


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        StringBuffer  sb = null;
        for(int i=0;i<500000000;i++){

//              sb = craeteStringBuffer("1","2");
            craeteStringBuffer("1","2");

        }
//        System.out.println(sb.toString());

        System.out.println(System.currentTimeMillis() - start);
    }
    public static StringBuffer craeteStringBuffer(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
//        sb.append(s1);
//        sb.append(s2);
        return sb;
    }

    public static String createStringBuffer(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
//        sb.append(s1);
//        sb.append(s2);
        return "";
    }

}




