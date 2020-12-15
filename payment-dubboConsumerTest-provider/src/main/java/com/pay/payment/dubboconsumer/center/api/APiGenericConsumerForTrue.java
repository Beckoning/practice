package com.pay.payment.dubboconsumer.center.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;


public class APiGenericConsumerForTrue {
	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws Exception {
		// 1.泛型参数固定为GenericService
		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));

		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");

		// 2. 设置为泛化引用，类型为true
		referenceConfig.setInterface("com.pay.payment.dubboprovider.center.servcie.GreetingService");
		referenceConfig.setGeneric(true);

		// 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
		GenericService greetingService = referenceConfig.get();

		// 4.泛型调用， 基本类型以及Date,List,Map等不需要转换，直接调用,如果返回值为POJO也将自动转成Map
		Object result = greetingService.$invoke("sayHello", new String[] { "java.lang.String" },
				new Object[] { "world" });

		System.out.println(JSON.json(result));

		// 5. POJO参数转换为map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("class", "com.pay.payment.dubboprovider.center.common.PoJo");
		map.put("id", "1990");
		map.put("name", "jiaduo");

		// 6.发起泛型调用
		result = greetingService.$invoke("testGeneric", new String[] { "com.pay.payment.dubboprovider.center.common.PoJo" },
				new Object[] { map });
		System.out.println((result));
		new CountDownLatch(1).await();
	}
}