package com.pay.payment.kafka.provder.center;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

public class MyKafkaProducer {

    public static final String  BOOTSTRAP_SERVERS_CONFIG = "106.55.159.72:9092";
    public static void main(String[] args) throws Exception{
        //生产者三个属性必须指定(broker地址清单，key和value的序列化器)
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(kafkaProperties);
        for (int i = 0; i < 10; i++) {
            ProducerRecord record = new ProducerRecord("order", "key" + i, "message" + i);
            try {
                Future result = producer.send(record);
//                System.out.println(result.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new CountDownLatch(1).await();

    }
}