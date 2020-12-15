package com.pay.payment.kafka.provder.center;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {

    public static final String  BOOTSTRAP_SERVERS_CONFIG = "106.55.159.72:9092";

    public static void main(String[] args) {
        //消费者三个属性必须指定(broker地址清单，key和value的序列化器)
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_0");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //1.创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //2.订阅Topic
        //创建一个只包含单个元素的列表，Topic的名字叫作customerCountries，可以订阅多个主题
        consumer.subscribe(Collections.singletonList("hello-kafka"));
        //支持正则表达式，订阅所有与test相关的Topic
        //consumer.subscribe("test.*");

        //3.轮询
        //消息轮询是消费者的核心API，通过一个简单的轮询向服务器请求数据，一旦消费者订阅了Topic，轮询就会处理所欲的细节，包括群组协调、partition再均衡、发送心跳
        //以及获取数据，开发者只要处理从partition返回的数据即可。
        try {
            while (true) {//消费者是一个长期运行的程序，通过持续轮询向Kafka请求数据。在其他线程中调用consumer.wakeup()可以退出循环
                //在100ms内等待Kafka的broker返回数据.超时参数指定poll在多久之后可以返回，不管有没有可用的数据都要返回
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, String> record : records) {
                       System.out.println(String.format("主题：%s, 分区：%d, 偏移量：%d, key:%s, value:%s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value()));                    //统计各个地区的客户数量，即模拟对消息的处理
                }
            }
        } finally {
            //退出应用程序前使用close方法关闭消费者，网络连接和socket也会随之关闭，并立即触发一次再均衡
            consumer.close();
        }
    }
}
