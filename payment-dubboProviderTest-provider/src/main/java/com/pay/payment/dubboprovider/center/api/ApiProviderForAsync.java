package com.pay.payment.dubboprovider.center.api;

import java.io.IOException;


import com.pay.payment.dubboprovider.center.servcie.GrettingServiceAsync;
import com.pay.payment.dubboprovider.center.servcie.impl.GrettingServiceAsyncImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;


public class ApiProviderForAsync {

	private static String applicationName = "payment-dubboProviderTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws IOException {

		// 1.创建服务发布实例，并设置
		ServiceConfig<GrettingServiceAsync> serviceConfig = new ServiceConfig<GrettingServiceAsync>();
		serviceConfig.setApplication(new ApplicationConfig(applicationName));
		serviceConfig.setRegistry(new RegistryConfig(registryAddress));
		serviceConfig.setInterface(GrettingServiceAsync.class);
		serviceConfig.setRef(new GrettingServiceAsyncImpl());
		serviceConfig.setVersion("1.0.0");
		serviceConfig.setGroup("dubbo");

		// 2.设置线程池策略
		// HashMap<String, String> parameters = new HashMap<>();
		// parameters.put("threadpool", "mythreadpool");
		// serviceConfig.setParameters(parameters);

		// 3.导出服务
		serviceConfig.export();

		// 4.阻塞线程
		System.out.println("server is started");
		System.in.read();
	}
}