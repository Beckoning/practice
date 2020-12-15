package com.pay.payment.dubboconsumer.center.api;

import java.util.concurrent.ExecutionException;

import com.pay.payment.dubboprovider.center.servcie.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;


/**
 * dubbo 2.7.0之前的版本异步调用方式1
 */
public class APiAsyncConsumer {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1.创建引用实例，并设置属性
		ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(UserService.class);
		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");
		referenceConfig.setTimeout(500000);

		//2. 设置为异步
		referenceConfig.setAsync(true);

		//3. 直接返回null
		UserService userService = referenceConfig.get();
		System.out.println(userService.sayHello("world"));

		//4.等待结果
		java.util.concurrent.Future<String> future = RpcContext.getContext().getFuture();
		//future.get() 阻塞线程 直到结果返回
		System.out.println(future.get());

	}
}