package com.pay.payment.dubboprovider.center.api;

import java.io.IOException;

import com.pay.payment.dubboprovider.center.servcie.GrettingServiceRpcContext;
import com.pay.payment.dubboprovider.center.servcie.impl.GrettingServiceAsyncContextImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;



public class ApiProviderForAsyncContext {

	private static String applicationName = "payment-dubboProviderTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";



	public static void main(String[] args) throws IOException {

		ServiceConfig<GrettingServiceRpcContext> serviceConfig = new ServiceConfig<GrettingServiceRpcContext>();
		serviceConfig.setApplication(new ApplicationConfig(applicationName));
		serviceConfig.setRegistry(new RegistryConfig(registryAddress));
		serviceConfig.setInterface(GrettingServiceRpcContext.class);
		serviceConfig.setRef(new GrettingServiceAsyncContextImpl());
		
		serviceConfig.setVersion("1.0.0");
		serviceConfig.setGroup("dubbo");
		
		//设置线程池策略
//		HashMap<String, String> parameters = new HashMap<>();
//		parameters.put("threadpool", "mythreadpool");
//		serviceConfig.setParameters(parameters);
		
		serviceConfig.export();

		System.out.println("server is started");
		System.in.read();
	}
}
