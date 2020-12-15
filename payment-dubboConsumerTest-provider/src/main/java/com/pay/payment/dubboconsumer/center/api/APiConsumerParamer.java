package com.pay.payment.dubboconsumer.center.api;

import com.pay.payment.dubboprovider.center.servcie.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;


/**
 * 隐式传递参数
 */
public class APiConsumerParamer {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";

	public static void main(String[] args) {
		ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(UserService.class);

//		referenceConfig.setVersion("1.0.0");
//		referenceConfig.setGroup("dubbo");
		
		UserService userService = referenceConfig.get();

		// 设置隐式参数
		RpcContext.getContext().setAttachment("company", "alibaba");

		System.out.println(userService.sayHello("world"));
	}
}