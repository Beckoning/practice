package com.pay.payment.mq.center.mq;

import com.pay.payment.mq.center.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RocketMQMessageListener(topic = Constant.PUSH_MESSAGE_TOPIC, consumerGroup = Constant.PUSH_MESSAGE_GROUP)
public class PushMessageConsumer  implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    int i = 0;
    @Override
    public void onMessage(MessageExt message) {

        System.out.println(i++);
//        throw new RuntimeException("错误啦");
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        defaultMQPushConsumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
}
