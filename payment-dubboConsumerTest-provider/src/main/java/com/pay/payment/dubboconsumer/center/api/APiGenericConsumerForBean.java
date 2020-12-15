package com.pay.payment.dubboconsumer.center.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;


public class APiGenericConsumerForBean {


	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws IOException {
		// 1.泛型参数固定为GenericService
		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));

//		referenceConfig.setVersion("1.0.0");
//		referenceConfig.setGroup("dubbo");

		// 2. 设置为泛化引用，并且泛化类型为bean
		referenceConfig.setInterface("com.opay.payment.user.provider.facade.UserFacade");
		referenceConfig.setGeneric("bean");

		// 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
		GenericService greetingService = referenceConfig.get();

		// 4.泛型调用，参数使用JavaBean进行序列化
		JavaBeanDescriptor param = JavaBeanSerializeUtil.serialize("156619112866619564");
		Object result = greetingService.$invoke("getUserByUserId", new String[] { "java.lang.String" },
				new Object[] { param });

		// 5.结果反序列化
		result = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result);
		System.out.println(result);

	}
}