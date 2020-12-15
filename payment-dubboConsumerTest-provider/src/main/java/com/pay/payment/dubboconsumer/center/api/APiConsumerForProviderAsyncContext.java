package com.pay.payment.dubboconsumer.center.api;

import com.pay.payment.dubboprovider.center.servcie.GrettingServiceRpcContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;


/**
 * 如果服务提供者使用AsyncContext异步处理请求
 * 服务消费者开发如下
 */

@Slf4j
public class APiConsumerForProviderAsyncContext {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	/**
	 * 异步调用2
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ReferenceConfig<GrettingServiceRpcContext> referenceConfig = new ReferenceConfig<GrettingServiceRpcContext>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		referenceConfig.setInterface(GrettingServiceRpcContext.class);
		referenceConfig.setTimeout(5000);

		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");

		GrettingServiceRpcContext greetingService = referenceConfig.get();

		//设置隐士参数

		RpcContext.getContext().setAttachment("company", "alibaba");
		log.info("-----------sayHello before");
		String result = greetingService.sayHello("world");
		log.info("-----------sayHello end");

		System.out.println(result);


	}
}