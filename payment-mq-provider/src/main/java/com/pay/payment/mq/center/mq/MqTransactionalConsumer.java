package com.pay.payment.mq.center.mq;

import com.pay.payment.mq.center.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class MqTransactionalConsumer {


    private DefaultMQPushConsumer pushConsumer;


    @Value("${rocketmq.name-server}")
    private String namesrvAddr;



    @PostConstruct
    public void init(){

         pushConsumer = new DefaultMQPushConsumer();
         pushConsumer.setConsumerGroup(Constant.TRANSACTIONAL_GROUP);
         pushConsumer.setNamesrvAddr(namesrvAddr);
         //程序第一次启动从消息队列头取数据
         pushConsumer.setConsumeFromWhere(
                ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
         try{
             pushConsumer.subscribe(Constant.TRANSACTIONAL_TOPIC,"*");

             pushConsumer.registerMessageListener(new MessageListenerConcurrently(){
                 @Override
                 public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                     MessageExt msg = msgs.get(0);
                     System.out.println(new String(msg.getBody()));
                     log.info("[ MQ异步回盘 TradePostConsumer ]");


                     return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                 }
             });
             pushConsumer.start();
         }catch (Exception e){

             e.printStackTrace();
         }
    }

}
