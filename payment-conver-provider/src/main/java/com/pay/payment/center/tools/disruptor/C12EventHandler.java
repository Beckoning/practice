package com.pay.payment.center.tools.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * C1-2消费者类
 * 该消费者类执行将数值乘以10的操作。
 */
public class C12EventHandler implements EventHandler<LongEvent>, WorkHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        long number = event.getNumber();
        number *= 10;
        System.out.println(System.currentTimeMillis()+": c1-2 consumer finished.number=" + number);
    }

    @Override
    public void onEvent(LongEvent event) throws Exception {
        long number = event.getNumber();
        number *= 10;
        System.out.println(Thread.currentThread().getName()+"-"+System.currentTimeMillis()+": c1-2 consumer finished.number=" + number);
    }
}