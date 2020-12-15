package com.pay.payment.dubboconsumer.center.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.pay.payment.dubboprovider.center.servcie.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * dubbo 2.7.0之后的版本异步调用方式2
 */
public class APiAsyncConsumerForCompletableFuture2 {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 1.创建服务引用对象，并设置数据
		ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(UserService.class);
		referenceConfig.setTimeout(30000);
		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");

		// 2. 设置为异步
		referenceConfig.setAsync(true);

		// 3. 直接返回null
		UserService userService = referenceConfig.get();
		System.out.println(userService.sayHello("world"));

		// 4.异步执行回调
		CompletableFuture<String> future = RpcContext.getContext().getCompletableFuture();
		future.whenComplete((v, t) -> {
			if (t != null) {
				t.printStackTrace();
			} else {
				System.out.println(v);
			}

		});

		System.out.println("over");
		Thread.currentThread().join();

	}
}