package com.pay.payment.reids;


import com.pay.payment.redis.center.RedisApplication;
import com.pay.payment.redis.center.service.RedisService;
import com.pay.payment.redis.center.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
@Slf4j
public class RedisTest {

    @Resource
    RedisService redisService;
    @Resource
    private RedissonService redissonService;

    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void testSet() {
        String key = "TEST+111010101010101";
        redisService.set(key, "111");

        System.out.println(redisService.get(key));
//        redisService.addSet(key,"11100");
//        redisService.addSet(key,"11100999");


//
//        System.out.println(redisService.exists(key)+"---------------------");
//        Set<Object> objectSet = redisService.getSet(key);
//        for(Object value : objectSet){
//            System.out.println((String)value);
//        }
//
//        //删除key
//        redisService.remove(key);
//
//        System.out.println(redisService.checkKeyAndValue(key, "111")+"---------------------");


        redisService.zSet(key, "111", 1);
        redisService.zSet(key, "1112", 5);
        redisService.zSet(key, "1111", 4);
        redisService.zSet(key, "1111", 3);


//
//        Set<Object> objectSet = redisService.rangeByScore(key,1,4);
//
//        for(Object obj: objectSet){
//            System.out.println((String)obj);
//        }
//
//        System.out.println();


        Map<Object, Double> map = redisService.rangeByScoreWithScores(key, 1, 4);

        for (Object obj : map.keySet()) {
            System.out.println("obj = " + obj + "   " + "scores = " + map.get(obj));
        }


    }


    @Test
    public void testsetOverwriteValue() {
        String key = "bitTest2";
//        redisService.set(key,"hello world");
//        System.out.println(redisService.get(key)+"----------");
//        System.out.println(redisService.setOverwriteValue(key, "redis", 6));
//        System.out.println(redisService.get(key)+"----------");
//
//        System.out.println(redisService.get(key));
//
//        System.out.println(redisService.getAndSet(key, "name123"));
//
//        System.out.println(redisService.get(key));
//
//
//
//        System.out.println("11x");

        redisService.set(key, "a");
        // 'a' 的ASCII码是 97。转换为二进制是：01100001
        // 'b' 的ASCII码是 98  转换为二进制是：01100010
        // 'c' 的ASCII码是 99  转换为二进制是：01100011
        //因为二进制只有0和1，在setbit中true为1，false为0，因此我要变为'b'的话第六位设置为1，第七位设置为0
        System.out.println(redisService.get(key));

        redisService.setBit(key, 6, true);
        System.out.println(redisService.get(key));
        redisService.setBit(key, 7, false);
        System.out.println(redisService.get(key));


    }


    @Test
    public void testdistinctRandomMembers() {
        String key = "setZTest";
//        redisTemplate.opsForSet().add(key,"1");
//        redisTemplate.opsForSet().add(key,"5");
//        redisTemplate.opsForSet().add(key,"3");
//        redisTemplate.opsForSet().add(key,"2");
//        redisTemplate.opsForSet().add(key,"2");
//        Set<String> stringSet = redisTemplate.opsForSet().distinctRandomMembers(key,5);
//        System.out.println("---------------"+redisTemplate.opsForSet().size(key));
//
//        for(String obj: stringSet){
//            System.out.println(obj);
//        }


//        List<String> stringList = redisTemplate.opsForSet().randomMembers(key,5);
//        System.out.println("---------------"+redisTemplate.opsForSet().size(key));
//        for(String value : stringList){
//            System.out.println(value);
//        }

//        Cursor<Object> curosr = redisTemplate.opsForSet().scan("setTest", ScanOptions.NONE);
//        System.out.println("---------------"+redisTemplate.opsForSet().size(key));
//
//        while(curosr.hasNext()){
//            System.out.println(curosr.next());
//        }


        redisTemplate.opsForZSet().add(key, "1", 1);
        redisTemplate.opsForZSet().add(key, "5", 5);
        redisTemplate.opsForZSet().add(key, "2", 2);
        redisTemplate.opsForZSet().add(key, "3", 3);
        redisTemplate.opsForZSet().add(key, "9", 9);
        System.out.println("-----------" + redisTemplate.opsForZSet().size(key));

        System.out.println(redisTemplate.opsForZSet().rangeByScore(key, 1, 2));


        System.out.println(redisTemplate.opsForZSet().rangeByScore(key, 1, 1, 1, 9));


    }

    @Test
    public void testGeo() {
        String key = "geoKey";
//        redisTemplate.opsForGeo().add(key,)
    }

    @Test
    public void testBit() {
        String key = "testBit4";
        System.out.println(redisTemplate.opsForValue().setBit(key, 6, true));
        System.out.println(redisTemplate.opsForValue().setBit(key, 7, false));

        System.out.println("-------------" + redisTemplate.opsForValue().getBit(key, 6));
        System.out.println("-------------" + redisTemplate.opsForValue().getBit(key, 7));
        System.out.println("-------------" + redisTemplate.opsForValue().get(key));


    }


    @Test
    public void testOffset() {
        String key = "offsetKey1";
        redisTemplate.opsForValue().set(key, "name value");
        System.out.println("----------" + redisTemplate.opsForValue().get(key));
        redisTemplate.opsForValue().set(key, "----", 4);
        System.out.println("----------" + redisTemplate.opsForValue().get(key));


    }

    @Test
    public void testhllOps() {
        String key = "hllopsKey";
        String key1 = "hllopsKey1";
        String key2 = "hllopsKey4";

        redisTemplate.opsForHyperLogLog().add(key, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        redisTemplate.opsForHyperLogLog().add(key1, 'a', 'w', 'e', 'r', 't', 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);

//        redisTemplate.opsForHyperLogLog().add(key2,'a','w','e','r','t');

//        System.out.println(redisTemplate.opsForHyperLogLog().size(key));
//        System.out.println(redisTemplate.opsForHyperLogLog().size(key1));
//        System.out.println(redisTemplate.opsForHyperLogLog().size(key2));
//
//
//        redisTemplate.opsForHyperLogLog().union(key2,key,key1);
//        System.out.println(redisTemplate.opsForHyperLogLog().size(key2));
//
//        System.out.println(redisTemplate.opsForHyperLogLog().size(key));
//        System.out.println(redisTemplate.opsForHyperLogLog().size(key1));
        System.out.println(redisTemplate.opsForHyperLogLog().size(key1, key));


    }

    /**
     * lock
     */
    @Test
    public void testtryLock() {

        String recordId = "we";
        RLock lock = redissonService.getRLock(recordId);
        try {
            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                // 业务代码
                log.info("进入业务代码: " + recordId);

                lock.unlock();
            } else {
                Thread.sleep(300);
            }
        } catch (Exception e) {
            log.error("", e);
            lock.unlock();
        }
    }


    @Test
    public void testlockInterruptibly() {

        String recordId = "we";
        RLock lock = redissonService.getRLock(recordId);
        try {
            lock.lockInterruptibly(6, TimeUnit.SECONDS);

            // 业务代码
            log.info("进入业务代码: " + recordId);
            lock.unlock();

        } catch (Exception e) {
            log.error("", e);
            lock.unlock();
        }
    }
}

