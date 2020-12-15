package com.pay.payment.dubboconsumer.center.api;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;


/**
 * 如果与你调试的dubbo接口还没有实现可以使用这个方式 mock数据
 */
public class APiConsumerMock {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";

	public static void main(String[] args) throws InterruptedException {
		// 0.创建服务引用对象实例
		ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<GreetingService>();
		// 1.设置应用程序信息
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		// 2.设置服务注册中心
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
		// 3.设置服务接口和超时时间
		referenceConfig.setInterface(GreetingService.class);
		referenceConfig.setTimeout(5000);

		// 4.设置服务分组与版本
		referenceConfig.setVersion("1.0.0");
		referenceConfig.setGroup("dubbo");

		// 5设置启动时候不检查服务提供者是否可用
		referenceConfig.setCheck(false);
		referenceConfig.setMock("true");
		// 6.引用服务
		GreetingService greetingService = referenceConfig.get();

		// 7. 设置隐式参数
		RpcContext.getContext().setAttachment("company", "alibaba");

		// 8调用服务
		System.out.println(greetingService.sayHello("world"));

		Map<String, Object> objectMap =  RpcContext.getServerContext().get();

	}
}