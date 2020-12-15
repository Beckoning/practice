package com.pay.payment.dubboconsumer.center.api;

import com.pay.payment.dubboprovider.center.servcie.GrettingServiceAsync;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;

/**
 * 如果服务提供者使用CompletableFuture异步处理请求
 * 服务消费者开发如下
 */
public class APiConsumerForProviderAsync {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	/**
     * 异步调用1
	 * @param args
     * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		//1.创建服务引用实例，并设置
		ReferenceConfig<GrettingServiceAsync> referenceConfig = new ReferenceConfig<GrettingServiceAsync>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(GrettingServiceAsync.class);
		referenceConfig.setTimeout(50000);
		//referenceConfig.setCluster("myCluster");

		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");

		//2.服务引用
		GrettingServiceAsync greetingService = referenceConfig.get();

		//3.设置隐士参数
		RpcContext.getContext().setAttachment("company", "alibaba");
		//RpcContext.getContext().set("ip", "30.39.148.197");

		//4.获取future并设置回调
		CompletableFuture<String> future = greetingService.sayHello("world");
		future.whenComplete((v, t) -> {
			if (t != null) {
				t.printStackTrace();
			} else {
				System.out.println(v);
			}

		});

//		Thread.currentThread().join();

	}
}