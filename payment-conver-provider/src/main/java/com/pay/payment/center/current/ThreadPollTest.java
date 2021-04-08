package com.pay.payment.center.current;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPollTest {

    ExecutorService  executorService = Executors.newCachedThreadPool();


    public static void main(String[] args) {

        LockTest lockTest = new LockTest();
        lockTest.setM("1");
        test(lockTest);
        System.out.println(JSON.toJSON(lockTest));

    }

    public static void test(LockTest lockTest){
        lockTest =  new LockTest();
        lockTest.setM("2");
        lockTest.setM("2");
        System.out.println();
    }
}

