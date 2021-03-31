package com.pay.payment.mq.center.mq;

import com.pay.payment.mq.center.constant.Constant;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushMessageProvider {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public Boolean sendMessage(String message){
        //同步
        SendResult sendResult = rocketMQTemplate.syncSend(Constant.PUSH_MESSAGE_TOPIC,message);

        if(SendStatus.SEND_OK.equals(sendResult.getSendStatus())){
            return true;
        }


        //异步
        rocketMQTemplate.asyncSend(Constant.PUSH_MESSAGE_TOPIC,message,new SendCallback(){
            //发送成功
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            //发送失败
            @Override
            public void onException(Throwable e) {

            }
        });
        return false;
    }

}
