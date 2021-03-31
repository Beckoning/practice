package com.pay.payment.center.dealMethod;



import java.text.DecimalFormat;


/**
 * 测试final修饰 对象
 */
public class TestFinal {

    public static void main(String[] args) {


//        for (int i=0;i<10;i++) {
//            EntityExample entityExample = createFinalEntityExampl(i+"");
//            System.out.println(entityExample.toString());
//        }

        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        String FUND_BAL_TMP = decimalFormat.format(10000000000.00);//会返回格式化后的金额
        System.out.println(FUND_BAL_TMP);

        System.out.println(Double.parseDouble("2323.32"));





    }
//
    private static EntityExample createFinalEntityExampl(String orderNo) {

        //写法1 有问题 EntityExample使用final修饰后该对象的地址值不可以改变  但是其内容值可以改变
//        final EntityExample entityExample = new EntityExample(orderNo);
//        entityExample = new EntityExample();

        //写法二 正确   useEntityExample这个方法使用的EntityExample对象不可以改变地址值，防止后期地址发生改变
        // （该对象是地址传递，可以通过该对象执行后的信息）
        final EntityExample entityExample = new EntityExample(orderNo);

        //使用EntityExample对象
        useEntityExample(entityExample);

        return entityExample;
    }


    private static EntityExample useEntityExample(EntityExample entityExample) {


        return entityExample;
    }

}
