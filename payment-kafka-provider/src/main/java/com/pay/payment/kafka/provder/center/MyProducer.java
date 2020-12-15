package com.pay.payment.kafka.provder.center;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class MyProducer  {
    private static KafkaProducer<String,String> producer;

    public static final String  BOOTSTRAP_SERVERS_CONFIG = "106.55.159.72:9092";

    static {
        Properties properties = new Properties();
        properties.put("bootstrap.servers",BOOTSTRAP_SERVERS_CONFIG);
        properties.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 第一种直接发送，不管结果
     */
    private static void sendMessageForgetResult(){
        ProducerRecord<String,String> record = new ProducerRecord<String,String>(
                "kafka-study","name","Forget_result"
        );
        producer.send(record);
//        producer.close();
    }

    /**
     * 第二种同步发送，等待执行结果
     * @return
     * @throws Exception
     */
    private static RecordMetadata sendMessageSync() throws Exception{
        ProducerRecord<String,String> record = new ProducerRecord<String,String>(
                "kafka-study","name","sync"
        );
        RecordMetadata result = producer.send(record).get();
        System.out.println(result.topic());
        System.out.println(result.partition());
        System.out.println(result.offset());
        return result;
    }
 
    /**
     * 第三种执行回调函数
     */
    private static void sendMessageCallback(){
        ProducerRecord<String,String> record = new ProducerRecord<String,String>(
                "kafka-study","name","callback"
        );
        producer.send(record,new MyProducerCallback());
    }
 
//    //定时任务
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        try {
//            sendMessageSync();
//        }catch (Exception e){
//            System.out.println("error:"+e);
//        }
//    }
 
    private static class MyProducerCallback implements Callback {
 
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e !=null){
                e.printStackTrace();
                return;
            }
            System.out.println(recordMetadata.topic());
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
            System.out.println("Coming in MyProducerCallback");
        }
    }
 
 
    public static void main(String[] args) throws Exception {
        sendMessageForgetResult();
        sendMessageSync();
        sendMessageCallback();


        new CountDownLatch(1).await();
//        JobDetail job = JobBuilder.newJob(MyProducer.class).build();
//
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever()).build();
//
//        try {
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.scheduleJob(job,trigger);
//            scheduler.start();
//        }catch (SchedulerException e){
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}