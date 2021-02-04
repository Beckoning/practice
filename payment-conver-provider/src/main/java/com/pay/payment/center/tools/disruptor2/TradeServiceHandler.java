package com.pay.payment.center.tools.disruptor2;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;

public class TradeServiceHandler implements EventHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {

        this.onEvent(event);
    }

    public void onEvent(TradeTransaction event) throws Exception{
        event.setId(UUID.randomUUID().toString());
        System.out.println("TradeServiceHandler"+"-------------"+event.getId());
    }
}
