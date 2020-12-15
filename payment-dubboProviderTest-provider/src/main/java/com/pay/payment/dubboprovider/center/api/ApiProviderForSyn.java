package com.pay.payment.dubboprovider.center.api;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import com.pay.payment.dubboprovider.center.servcie.UserService;
import com.pay.payment.dubboprovider.center.servcie.impl.GreetingServiceImpl;
import com.pay.payment.dubboprovider.center.servcie.impl.UserServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;


public class ApiProviderForSyn {


	private static String applicationName = "payment-dubboProviderTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws IOException {
		// 1.创建ServiceConfig实例
		ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
		// 2.设置应用程序配置
		serviceConfig.setApplication(new ApplicationConfig(applicationName));

		// 3.设置服务注册中心信息
		RegistryConfig registryConfig = new RegistryConfig(registryAddress);
		serviceConfig.setRegistry(registryConfig);
		// 4.设置接口与实现类
		serviceConfig.setInterface(UserService.class);
		serviceConfig.setRef(new UserServiceImpl());

		// 5.设置服务分组与版本 
		serviceConfig.setVersion("1.0.0");
		serviceConfig.setGroup("dubbo");

		// 6.设置线程池策略
//		HashMap<String, String> parameters = new HashMap<>();
//		parameters.put("threadpool", "mythreadpool");
//		serviceConfig.setParameters(parameters);

		// 7.导出服务
		serviceConfig.export();


//		// 1.创建ServiceConfig实例
//		ServiceConfig<GreetingService> serviceConfig2 = new ServiceConfig<GreetingService>();
//		// 2.设置应用程序配置
//		serviceConfig2.setApplication(new ApplicationConfig(applicationName));
//
//		// 3.设置服务注册中心信息
//		RegistryConfig registryConfig2 = new RegistryConfig(registryAddress);
//		serviceConfig2.setRegistry(registryConfig2);
//		// 4.设置接口与实现类
//		serviceConfig2.setInterface(GreetingService.class);
//		serviceConfig2.setRef(new GreetingServiceImpl());
//
//		// 5.设置服务分组与版本
//		serviceConfig2.setVersion("1.0.0");
//		serviceConfig2.setGroup("dubbo");
//		serviceConfig2.export();


		// 8.挂起线程，避免服务停止
		System.out.println("server is started");
		System.in.read();
	}
}
