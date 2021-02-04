package com.pay.payment.center.tools.disruptor2;

import java.util.concurrent.*;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;


/**
 * Disruptor2.0用法
 */
public class Demo1 {
	public static void main(String[] args)throws InterruptedException,ExecutionException{
		final int BUFFER_SIZE = 1024;
		final int THREAD_NUMBERS = 2;
		/*
		 * createSingleProducer创建一个单生产者的RingBuffer
		 * 第一个参数叫EventFactory,从名字上理解就是"事件工厂"，其实它的职责就是产生数据填充RingBuffer的区块
		 * 第二参数是RingBuffer的大小，它必须是2的指数倍，目的是为了将求棋运算转为&运算提高效率
		 * 第三个参数是RingBuffer的生产都在没有可用区块（slot）的时候（可能是消费者(或者说是事件处理器)太慢了）的等待策略
		 */
		final RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<TradeTransaction>(){
			@Override
			public TradeTransaction newInstance() {
				return new TradeTransaction();
			}
		}, BUFFER_SIZE,new YieldingWaitStrategy());
		
		//创建线程池
		ExecutorService executors = new ThreadPoolExecutor(THREAD_NUMBERS,THREAD_NUMBERS,0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1));
//		ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);
		
		//创建SequenceBarrier 序号屏障器
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
		
		//创建消息处理器
		BatchEventProcessor<TradeTransaction> transProcessor = new BatchEventProcessor<TradeTransaction>(ringBuffer,sequenceBarrier,new TradeTransactionInDBHandler());

		//ringBuffer可以知晓消费者的状态
		ringBuffer.addGatingSequences(transProcessor.getSequence());
		
		executors.submit(transProcessor);

//		SequenceBarrier sequenceBarrier1 = ringBuffer.newBarrier();
//
//		BatchEventProcessor<TradeTransaction> transProcessor1 = new BatchEventProcessor<TradeTransaction>(ringBuffer,sequenceBarrier1,new TradeServiceHandler());
//
//		ringBuffer.addGatingSequences(transProcessor1.getSequence());
//
//		executors.submit(transProcessor1);
		
		Future<?> future = executors.submit(new Callable<Void>(){
 
			@Override
			public Void call() throws Exception {
				long seq;
				for(int i=0;i<1000;i++){
					seq = ringBuffer.next();
					ringBuffer.get(seq).setPrice(Math.random()*9999);
					ringBuffer.publish(seq);//发布这个slot的数据使handler(consumer)可见
				}
				return null;
			}
			
		});
		future.get();
		Thread.sleep(1000);
		transProcessor.halt();
//		transProcessor1.halt();

		executors.shutdown();
	}
}