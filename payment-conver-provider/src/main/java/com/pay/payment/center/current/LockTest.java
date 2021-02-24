package com.pay.payment.center.current;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {


    public static void main(String[] args) {
//        Lock lock = new ReentrantLock();
//        lock.lock();
//
////        lock.unlock();
//
//        try{
//            Thread.currentThread().interrupt();
//            System.out.println(Thread.interrupted());
//
//            System.out.println(Thread.currentThread().isInterrupted());
//
//            System.out.println(Thread.holdsLock(lock));
//            Thread.sleep(100);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Thread.currentThread().interrupt();
//
//        System.out.println(Thread.currentThread().isInterrupted());

        tetSynchrinized();



    }



    public static synchronized void tetSynchrinized(){


        System.out.println(Thread.holdsLock(LockTest.class));
    }

}
