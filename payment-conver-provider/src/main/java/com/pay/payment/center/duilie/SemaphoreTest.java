package com.pay.payment.center.duilie;

import java.util.Random;
import java.util.concurrent.*;

public class SemaphoreTest {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private static Random random = new Random();

    //阻塞队列
    private static BlockingQueue<String> parks = new LinkedBlockingQueue<>(5);


    public static void execute(Semaphore semaphore) {
        //获取一个随机数
        long sleepTime = random.nextInt(10);
        long threadId = Thread.currentThread().getId();
        String park = null;
        try {
            /**
             * 获取许可，首先判断semaphore内部的数字是否大于0，如果大于0，
             * 才能获得许可，然后将初始值5减去1，线程才会接着去执行；如果没有
             * 获得许可(原因是因为已经有5个线程获得到许可，semaphore内部的数字为0)，
             * 线程会阻塞直到已经获得到许可的线程，调用release()方法，释放掉许可，
             * 也就是将semaphore内部的数字加1，该线程才有可能获得许可。
             */
            semaphore.acquire();
            /**
             *  对应的线程会到阻塞对，对应车辆去获取到车位，如果没有拿到一致阻塞，
             *  直到其他车辆归还车位。
             */
            park = parks.take();
            System.out.println("线程ID" + threadId + ",开始占用车位:" + park + "，当前剩余车位" + semaphore.availablePermits());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //睡眠随机秒
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //归还车位
        parks.offer(park);
        System.out.println("线程ID" + threadId + ",开始归还车位:" + park + ",共占用" + sleepTime + "秒");
        //线程释放掉许可，通俗来将就是将semaphore内部的数字加1
        semaphore.release();
    }

    public static void main(String[] args) {
        //初始化线程数量
        int threadNum = 100;
        parks.offer("车位一");
        parks.offer("车位二");
        parks.offer("车位三");
        parks.offer("车位四");
        parks.offer("车位五");


        // 初始化5个许可证
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < threadNum; i++) {
            executorService.submit(() -> {
                execute(semaphore);
            });
        }
    }
}