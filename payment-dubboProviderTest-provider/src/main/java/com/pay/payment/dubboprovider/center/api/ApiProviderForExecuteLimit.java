package com.pay.payment.dubboprovider.center.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import com.pay.payment.dubboprovider.center.servcie.UserService;
import com.pay.payment.dubboprovider.center.servcie.impl.GreetingServiceImpl;
import com.pay.payment.dubboprovider.center.servcie.impl.UserServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;


/**
 * 服务提供端并发消费次数
 * 如果请求数超过设置的executes值 则会抛异常
 */
public class ApiProviderForExecuteLimit {

	private static String applicationName = "payment-dubboProviderTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws IOException {

		ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
		serviceConfig.setApplication(new ApplicationConfig(applicationName));
		serviceConfig.setRegistry(new RegistryConfig(registryAddress));
		serviceConfig.setInterface(UserService.class);
		serviceConfig.setRef(new UserServiceImpl());
		
//		serviceConfig.setVersion("1.0.0");
//		serviceConfig.setGroup("dubbo");
		
		//设置并发控制数
		serviceConfig.setExecutes(10);

		final List<MethodConfig> methodList = new ArrayList<MethodConfig>();

		//特定方法设置并发数量
		MethodConfig methodConfig = new MethodConfig();
		methodConfig.setExecutes(10);
		methodConfig.setName("sayHello");
		methodList.add(methodConfig);
		serviceConfig.setMethods(methodList);

		// 设置线程池策略
//		HashMap<String, String> parameters = new HashMap<>();
//		parameters.put("threadpool", "mythreadpool");
//		serviceConfig.setParameters(parameters);

		serviceConfig.export();

		System.out.println("server is started");
		System.in.read();
	}
}
