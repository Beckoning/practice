package com.pay.payment.dubboconsumer.center.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import org.apache.dubbo.rpc.service.GenericService;

/**
 * 服务消费端并发控制
 * 并发请求的数大于actives时，请求会等待，如果等待超时则 直接抛出异常，这时服务根本没有发送到服务提供方服务器
 */
public class APiConsumerForActiveLimit {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args)throws Exception {
		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(GenericService.class);
//
//		referenceConfig.setVersion("1.0.0");
//		referenceConfig.setGroup("dubbo");
//
		//设置激活并发限制个数
		referenceConfig.setActives(10);

		//特定方法设置并发数量
//		final List<MethodConfig> methodList = new ArrayList<MethodConfig>();
//
//		MethodConfig methodConfig = new MethodConfig();
//		methodConfig.setActives(10);
//		methodConfig.setName("getUserByUserId");
//		methodList.add(methodConfig);
//		referenceConfig.setMethods(methodList);

		// 2. 设置为泛化引用，类型为true
        referenceConfig.setInterface("com.opay.payment.user.provider.facade.UserFacade");
		referenceConfig.setGeneric(true);

		// 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
		GenericService greetingService = referenceConfig.get();


		
		//设置隐式参数
//		RpcContext.getContext().setAttachment("company", "alibaba");

        // 4.泛型调用， 基本类型以及Date,List,Map等不需要转换，直接调用,如果返回值为POJO也将自动转成Map
        Object result = greetingService.$invoke("getUserByUserId", new String[] { "java.lang.String" },
                new Object[] { "156619112866619564" });

        System.out.println(JSON.json(result));
	}
}     