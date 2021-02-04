package com.pay.payment.center.tools.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 * Disruptor3.0用法
 */
public class DisruptorTestMain {
    public static void main(String[] args) {
        int bufferSize = 1024*1024;//环形队列长度，必须是2的N次方
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
//        ExecutorService executor = Executors.newCachedThreadPool();

        /**
         * 定义Disruptor，基于单生产者，阻塞策略
         */
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory,bufferSize, Executors.defaultThreadFactory(), ProducerType.MULTI,new BlockingWaitStrategy());
        /////////////////////////////////////////////////////////////////////
        parallelWithOnePool(disruptor);//这里是调用各种不同方法的地方.
        /////////////////////////////////////////////////////////////////////
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        RingBuffer<LongEvent> ringBuffer1 = disruptor.getRingBuffer();

        /**
         * 输入10
         */
        for (int i=0;i<1;i++){
            ringBuffer.publishEvent(new LongEventTranslator(),Long.parseLong(i+""));

        }

        for (int i=10;i<11;i++){
            ringBuffer1.publishEvent(new LongEventTranslator(),Long.parseLong(i+""));

        }



//        ringBuffer.publishEvent(new LongEventTranslator(),100L);
    }

    /**
     * 并行计算实现,c1,c2互相不依赖
     * <br/>
     * p --> c11
     *   --> c21
     *                 |----消费者1
     *    生产者 - 数据 -|----消费者2
     *                -|----消费者3
     */
    public static void parallel(Disruptor<LongEvent> disruptor){
        disruptor.handleEventsWith(new C11EventHandler(),new C21EventHandler());
        disruptor.start();
    }


    /**
     * 串行依次执行
     * <br/>
     * p --> c11 --> c21
     *
     *    生产者 - 数据 ----消费者1----消费者2----消费者3
     *
     *
     * @param disruptor
     */
    public static void serial(Disruptor<LongEvent> disruptor){
        disruptor.handleEventsWith(new C11EventHandler()).then(new C21EventHandler());
        disruptor.start();
    }

    /**
     * 菱形方式执行
     * <br/>
     *   --> c11
     * p          --> c21
     *   --> c12
     * @param disruptor
     */
    public static void diamond(Disruptor<LongEvent> disruptor){
        disruptor.handleEventsWith(new C11EventHandler(),new C12EventHandler()).then(new C21EventHandler());
        disruptor.start();
    }

    /**
     * 链式并行计算
     * <br/>
     *   --> c11 --> c12
     * p
     *   --> c21 --> c22
     * @param disruptor
     */
    public static void chain(Disruptor<LongEvent> disruptor){
        disruptor.handleEventsWith(new C11EventHandler()).then(new C12EventHandler());
        disruptor.handleEventsWith(new C21EventHandler()).then(new C22EventHandler());
        disruptor.start();
    }

    /**
     * 并行计算实现,c1,c2互相不依赖,同时C1，C2分别有2个实例
     * <br/>
     * p --> c11 c11
     *   --> c21 c21
     */
    public static void parallelWithPool(Disruptor<LongEvent> disruptor){
        //如果使用一个handleEventsWithWorkerPool表示一个多个消费者消费一个
        disruptor.handleEventsWithWorkerPool(new C11EventHandler(),new C11EventHandler(),new C21EventHandler());
        disruptor.handleEventsWithWorkerPool(new C21EventHandler(),new C21EventHandler());
        disruptor.start();
    }

    /**
     * handleEventsWithWorkerPool表示一个数据池，多个消费者消费一个
     * 保证一条数据只能被一个消费者消费
     * @param disruptor
     */
    public static void parallelWithOnePool(Disruptor<LongEvent> disruptor){
        //
        disruptor.handleEventsWithWorkerPool(new C11EventHandler(),new C11EventHandler(),new C21EventHandler());
        disruptor.start();
    }

    /**
     * 串行依次执行,同时C11，C21分别有2个实例
     * <br/>
     * p --> c11 --> c21
     * @param disruptor
     */
    public static void serialWithPool(Disruptor<LongEvent> disruptor){
        disruptor.handleEventsWithWorkerPool(new C11EventHandler(),new C11EventHandler()).then(new C21EventHandler(),new C21EventHandler());
        disruptor.start();
    }
}