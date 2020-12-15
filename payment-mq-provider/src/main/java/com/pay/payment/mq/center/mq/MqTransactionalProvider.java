package com.pay.payment.mq.center.mq;

import com.pay.payment.mq.center.constant.Constant;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mq事务
 *
 * 发送端的功能:
 *  1、执行本地事务
 *  2、查询执行查询本地事务状态
 * 本地事务执行成功后，才向消费端真正发送mq
 * 消费端消费失败，可以重复发起（可能会重新验证事务状态）
 *
 */
@Component
public class MqTransactionalProvider {

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    private TransactionMQProducer transactionMQProducer;
    private TransactionListener transactionListener;
    int executeLocalTransactionTime  = 1;
    int checkLocalTransaction = 1;

    public SendResult send(){

        transactionMQProducer = new TransactionMQProducer(Constant.TRANSACTIONAL_GROUP);

        long time = System.currentTimeMillis();
        transactionListener = new TransactionListener() {

            /**
             * 根据消息发送的结果 判断是否执行本地事务
             *
             * 根据本地事务执行成与否判断 事务消息是否需要commit与 rollback
             * @param msg
             * @param arg
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                //执行本地
                new java.lang.String(msg.getBody());

                System.out.println(++executeLocalTransactionTime+"executeLocalTransaction");
                return LocalTransactionState.UNKNOW;
            }

            /**
             * RocketMQ 回调 根据本地事务是否执行成功 告诉broker 此消息是否投递成功
             *
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                //验证状态
                System.out.println(++checkLocalTransaction+"checkLocalTransaction");

                System.out.println(new String(msg.getBody()));

                if(checkLocalTransaction == 3){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                System.out.println("-----------"+(System.currentTimeMillis() - time));
                return LocalTransactionState.UNKNOW;
            }
        };
        transactionMQProducer.setTransactionListener(transactionListener);
        transactionMQProducer.setNamesrvAddr(namesrvAddr);

        try {
            transactionMQProducer.start();
            // 构造MSG，省略构造参数
            Message msg = new Message(Constant.TRANSACTIONAL_TOPIC,"xxx".getBytes());
            // 发送消息
            SendResult sendResult = transactionMQProducer.sendMessageInTransaction(msg, null);

            //关闭请求
//            transactionMQProducer.shutdown();

            return sendResult;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
