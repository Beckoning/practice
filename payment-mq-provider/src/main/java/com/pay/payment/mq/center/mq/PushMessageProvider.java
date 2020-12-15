package com.pay.payment.mq.center.mq;

import com.pay.payment.mq.center.constant.Constant;
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
        SendResult sendResult = rocketMQTemplate.syncSend(Constant.PUSH_MESSAGE_TOPIC,message);

        if(SendStatus.SEND_OK.equals(sendResult.getSendStatus())){
            return true;
        }
        return false;
    }

}
