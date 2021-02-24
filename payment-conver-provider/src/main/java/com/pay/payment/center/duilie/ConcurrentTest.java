package com.pay.payment.center.duilie;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTest {


    public static void main(String[] args)throws Exception {


//        CountDownLatch countDownLatch = new CountDownLatch(3);
//
//
//        for(int i=0;i<3;i++){
//            MyThread myThread = new MyThread(countDownLatch, new Random().nextInt(10));
//            myThread.start();
//        }
//
//        countDownLatch.await();

        latchTest();

    }


    /**
     * 实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
     * 类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。
     * 做法是初始化一个共享的CountDownLatch(1)，将其计算器初始化为1，
     * 多个线程在开始执行任务前首先countdownlatch.await()，当主线程调用countDown()时，计数器变为0，多个线程同时被唤醒。
     * @throws InterruptedException
     */
    public static   void latchTest() throws InterruptedException {
        //压测线程数
        int testThreads = 10;
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(testThreads);
        //创建线程池
        ExecutorService exce = Executors.newFixedThreadPool(testThreads);
        for (int i = 0; i < testThreads; i++) {
            exce.submit(() -> {
                try {
                    int time  = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName()+"准备"+time);
                    //启动后等待 唤醒
                    Thread.sleep(time);
                    start.await();
                    //压测具体方法
                    System.out.println(Thread.currentThread().getName()+"执行");

                    testLoad();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //结束CountDownLatch -1
                    end.countDown();
                }
            });

        }
        Thread.sleep(1000);
        //连接池线程初始化完成 开始压测
        start.countDown();
        //压测完成后结束
        end.await();
        exce.shutdown();
    }

    private static void testLoad() {
        System.out.println(1);
    }


    static class MyThread extends Thread{

        private CountDownLatch countDownLatch;
        private int time;

        public MyThread(CountDownLatch countDownLatch,int time) {
            this.countDownLatch = countDownLatch;
            this.time = time;
        }

        @Override
        public void run() {

            System.out.println(111);
            countDownLatch.countDown();

            System.out.println("12121212");

        }
    }


}
