package com.pay.payment.center.duilie;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    private static Random random = new Random();

    public static void execute(CyclicBarrier barrier) {
        //获取一个随机数
        long sleepTime = random.nextInt(10);
        long threadId = Thread.currentThread().getId();
        try {
            //睡眠随机秒
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程ID" + threadId + ",准备任务完成耗时：" + sleepTime + "当前时间" + System.currentTimeMillis());

        //线程等待其他任务完成后唤醒
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("线程ID" + threadId + ",开始执行任务，当前时间：" + System.currentTimeMillis());
    }


    public static void main(String[] args) {
        //初始化线程数量
        int threadNum = 10;
        //初始化一般的线程
        CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("整理任务开始..."));
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.submit(() -> {
                execute(barrier);
            });
        }
    }
}