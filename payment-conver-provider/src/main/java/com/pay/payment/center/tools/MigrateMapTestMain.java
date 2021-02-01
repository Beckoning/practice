package com.pay.payment.center.tools;
 
import com.google.common.base.Function;
import com.google.common.collect.MigrateMap;

import java.util.concurrent.ConcurrentMap;
 
 
/**
 * @author chenjunjie
 * @since 2018-9-26
 */
public class MigrateMapTestMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // makeComputingMap方法：根据this，创建一个NullComputingConcurrentMap或ComputingConcurrentMap。
        ConcurrentMap<String, String> testMap =  MigrateMap.makeComputingMap(new Function<String, String>() {
            /**
             * 这里就是绑定的根据key没找到value的时候触发的function，
             * 可以将这里的返回值放到对应的key的value中！
             * @param arg0
             * @return
             */
            public String apply(String arg0) {
                return "create:" + arg0;
            }
 
        });
 
        testMap.put("a", "testa");
        testMap.put("b", "testb");
 
        System.out.println(testMap.get("a"));
        System.out.println(testMap.get("b"));
        System.out.println(testMap.get("c"));
        System.out.println(testMap.get("d"));
 
        /**
         * 总结：
         * 运行结果可以知道，当map再调用get的时候如果根据key值获取不到value时，会触发function，执行apply方法。
         */


        //运行结果
        /**
         * testa
         * testb
         * create:c
         * create:d
         *
         * Process finished with exit code 0
         *
         *
         * 从运行结果可以知道，当map再调用get的时候如果根据key值获取不到value时，会触发function，执行apply方法，并获取apply返回的值。
         */
    }
}