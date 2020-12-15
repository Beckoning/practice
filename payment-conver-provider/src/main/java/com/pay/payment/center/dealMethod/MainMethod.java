package com.pay.payment.center.dealMethod;

/**
 *
 */
public class MainMethod {

    public static void main(String[] args) {
//        TestMethod testMethod = new TestMethod();
//
//        callDealMethod(testMethod.test("2323"));

        callFunctionmethod();
    }



   static void callDealMethod(String name){

       System.out.println(name+"callDealMethod");
    }


    static void callFunctionmethod(){
        ConvertRequest convertRequest = new ConvertRequest();
        convertRequest.eq(EntityExample::getSenderActualAmount,"wewe");

    }

}
