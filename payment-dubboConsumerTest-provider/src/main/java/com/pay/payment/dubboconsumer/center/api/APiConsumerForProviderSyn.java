package com.pay.payment.dubboconsumer.center.api;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import com.pay.payment.dubboprovider.center.servcie.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CountDownLatch;


public class APiConsumerForProviderSyn {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	/**
     * 同步调用
	 * @param args
     * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		//1.创建服务引用实例，并设置
		ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(UserService.class);
		referenceConfig.setTimeout(500000);
		referenceConfig.setRetries(0);
		//referenceConfig.setCluster("myCluster");

		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");
		// 5设置启动时候不检查服务提供者是否可用
		referenceConfig.setCheck(false);
//		referenceConfig.setMock("true");
//		referenceConfig.setGeneric(true);


		//2.服务引用
		UserService userService = referenceConfig.get();

		//3.设置隐士参数
		RpcContext.getContext().setAttachment("company", "alibaba");

		//4.获取future并设置回调
		String result = userService.sayHello();

		System.out.println("result = +"+result);

		new CountDownLatch(1).await();

	}
}