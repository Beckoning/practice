package com.pay.payment.center.tools.disruptor2;

import java.util.UUID;
 
import com.lmax.disruptor.EventHandler;
import org.apache.poi.ss.formula.functions.T;


public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>{
 
	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		this.onEvent(event);
	}
	
	public void onEvent(TradeTransaction event) throws Exception{
//		Thread.currentThread().sleep(1000);
		event.setId(UUID.randomUUID().toString());
		System.out.println(Thread.currentThread().getName()+"-------------"+event.getId());
	}
 
}