package com.pay.payment.center.tools.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * c2-1消费者类
 * 该消费者类负责将数值+20.
 */
public class C21EventHandler implements EventHandler<LongEvent>, WorkHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        long number = event.getNumber();
//        number += 20;
        System.out.println(System.currentTimeMillis()+": c2-1 consumer finished.number=" + number);
    }

    @Override
    public void onEvent(LongEvent event) throws Exception {
        long number = event.getNumber();
//        number += 20;
        System.out.println(Thread.currentThread().getName()+"-"+System.currentTimeMillis()+": c2-1 consumer finished.number=" + number);
    }
}