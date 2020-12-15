package com.pay.payment.dubboprovider.center.api;

import java.io.IOException;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import com.pay.payment.dubboprovider.center.servcie.UserService;
import com.pay.payment.dubboprovider.center.servcie.impl.GreetingServiceImpl;
import com.pay.payment.dubboprovider.center.servcie.impl.UserServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;


public class ApiProvider {


	private static String applicationName = "payment-dubboProviderTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws IOException {
		// 1.创建ServiceConfig实例
		ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<GreetingService>();
		// 2.设置应用程序配置
		serviceConfig.setApplication(new ApplicationConfig(applicationName));

		// 3.设置服务注册中心信息
		RegistryConfig registryConfig = new RegistryConfig(registryAddress);
		serviceConfig.setRegistry(registryConfig);
		// 4.设置接口与实现类
		serviceConfig.setInterface(GreetingService.class);
		serviceConfig.setRef(new GreetingServiceImpl());

		// 5.设置服务分组与版本 
		serviceConfig.setVersion("1.0.0");
		serviceConfig.setGroup("dubbo");

		// 6.设置线程池策略
//		HashMap<String, String> parameters = new HashMap<>();
//		parameters.put("threadpool", "mythreadpool");
//		serviceConfig.setParameters(parameters);

		// 7.导出服务
		serviceConfig.export();

		// 8.挂起线程，避免服务停止
		System.out.println("server is started");
		System.in.read();
	}
}
