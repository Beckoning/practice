package com.pay.payment.center.lock;

public class DeadLock{
    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                synchronized (lock1) {
                    System.out.println("线程1锁lock1");
                    try {
                        Thread.sleep(1000);
                        synchronized (lock2) {
                            System.out.println("线程1锁lock2");
                        }
                    } catch (InterruptedException e) {                  
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                synchronized (lock2) {
                    System.out.println("线程2锁lock2");
                    synchronized (lock1) {
                        System.out.println("线程2锁lock1");
                    }
                }
            }
        }).start();
    }
}
