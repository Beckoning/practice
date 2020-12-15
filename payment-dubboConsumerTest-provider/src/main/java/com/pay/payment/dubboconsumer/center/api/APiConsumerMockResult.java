package com.pay.payment.dubboconsumer.center.api;



import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;

/**
 * 服务降级  未调试成功
 */
public class APiConsumerMockResult {
	private static String applicationName = "payment-dubboConsumerTest-provider";
	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";

	public static void mockResult(String type) {
		// (1)获取服务注册中心工厂
		RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class)
				.getAdaptiveExtension();
		// (2)根据zk地址，获取具体的zk注册中心的客户端实例
		Registry registry2 = registryFactory.getRegistry(URL.valueOf(registryAddress));

//		 directory.subscribe(subscribeUrl.addParameter(CATEGORY_KEY,
		// PROVIDERS_CATEGORY + "," + CONFIGURATORS_CATEGORY + "," + ROUTERS_CATEGORY));

		// (3)注册降级方案到zk
//		registry2.register(URL.valueOf(
//				"override://0.0.0.0/com.pay.payment.dubboprovider.center.servcie.GreetingService?category=configurators&dynamic=false&application="+applicationName+"&"
//						+ "mock=" + type + ":return+null&group=dubbo&version=1.0.0"));

		//(4)取消配置
		registry2.unregister(URL.valueOf(
				"override://0.0.0.0/com.pay.payment.dubboprovider.center.servcie.GreetingService?category=configurators&dynamic=false&application="+applicationName+"&"
						+ "mock=" + type + ":return+null&group=dubbo&version=1.0.0"));
	}

	public static void main(String[] args) throws InterruptedException {

		// mock=force:result+null;
		mockResult("force");

		// mock=fail:result+null;
//		 mockResult("fail");

	}
}