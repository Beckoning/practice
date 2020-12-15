package com.pay.payment.center.dealMethod;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestAtomicLong {

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);
    private static final AtomicLong id = new AtomicLong(0);

    public static void main(String[] args)throws Exception {


//        Class<AtomicLong> atomicLongClass = (Class<AtomicLong>) INVOKE_ID.getClass();
//        Field messageField = atomicLongClass.getDeclaredField("valueOffset");
//        messageField.setAccessible(true); // 绕过权限检测！
//
//
//
//        Class<AtomicLong> idClass= (Class<AtomicLong>) id.getClass();
//        Field idFeld = idClass.getDeclaredField("valueOffset");
//        idFeld.setAccessible(true); // 绕过权限检测！

        System.out.println(INVOKE_ID.incrementAndGet());
        System.out.println(INVOKE_ID.getAndIncrement());
        System.out.println(id.getAndIncrement());




        System.out.println(Long.MAX_VALUE);
//        while (true){
//            if(10 ==id.get()){
//                break;
//            }
//
//            id.getAndIncrement();
//
//            System.out.println(INVOKE_ID.getAndIncrement());
//        }
    }
}
