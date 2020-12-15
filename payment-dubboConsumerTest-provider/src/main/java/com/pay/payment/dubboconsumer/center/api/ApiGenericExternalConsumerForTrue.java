package com.pay.payment.dubboconsumer.center.api;

import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class ApiGenericExternalConsumerForTrue {

    private static String applicationName = "payment-dubboConsumerTest-provider";

//    private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";

    public static  String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";
    public static void main(String[] args) throws Exception {
        // 1.泛型参数固定为GenericService
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
        referenceConfig.setApplication(new ApplicationConfig(applicationName));
        referenceConfig.setRegistry(new RegistryConfig(registryAddress));

//        referenceConfig.setVersion("1.0.0");
//        referenceConfig.setGroup("dubbo");

        // 2. 设置为泛化引用，类型为true
        referenceConfig.setInterface("com.opay.payment.user.provider.facade.UserFacade");
//        referenceConfig.setInterface("com.opay.merchant.provider.facade.MerchantFacade");
        referenceConfig.setGeneric(true);
//        referenceConfig.setAsync(true);


        // 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
        GenericService greetingService = referenceConfig.get();

        // 4.泛型调用， 基本类型以及Date,List,Map等不需要转换，直接调用,如果返回值为POJO也将自动转成Map
        Object result = greetingService.$invoke("getUserByUserId", new String[] { "java.lang.String" },
                new Object[] { "156619112866619564" });

        System.out.println(JSON.json(result));




//        ReferenceConfig<GenericService> referenceConfig1 = new ReferenceConfig<GenericService>();
//        referenceConfig1.setApplication(new ApplicationConfig(applicationName));
//        referenceConfig1.setRegistry(new RegistryConfig(registryAddress));
//
//        // 2. 设置为泛化引用，类型为true
////        referenceConfig1.setInterface("com.opay.payment.user.provider.facade.UserFacade");
//        referenceConfig1.setInterface("com.opay.merchant.provider.facade.MerchantFacade");
//
//        referenceConfig1.setGeneric(true);
//
//        // 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
//        GenericService greetingService1 = referenceConfig1.get();
//
//        // 4.泛型调用， 基本类型以及Date,List,Map等不需要转换，直接调用,如果返回值为POJO也将自动转成Map
//        Object result1 = greetingService1.$invoke("getMerchantInfo", new String[] { "java.lang.String" },
//                new Object[] { "156619112866619564" });
//
//        System.out.println(JSON.json(result1));


        new CountDownLatch(1).await();

//        // 5. POJO参数转换为map
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("class", "com.opay.merchant.provider.request.ModifyMerchantInfoRequest");
//        map.put("merchantId", "2323232323");
//        map.put("merchantName", "jiaduo");
//        map.put("operatorId","wewewewewewewe");
//        map.put("merchantType",null);
//
//        // 6.发起泛型调用
//        Object  result = greetingService.$invoke("updateMerchantInfoMsg", new String[] { "com.opay.merchant.provider.request.ModifyMerchantInfoRequest" },
//                new Object[] { map });
//        System.out.println((result));
    }
}
