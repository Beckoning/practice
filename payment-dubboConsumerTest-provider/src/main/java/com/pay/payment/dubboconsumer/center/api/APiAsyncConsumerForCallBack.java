//package com.pay.payment.dubboconsumer.center.api;
//
//import java.util.concurrent.ExecutionException;
//
//import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
//import com.pay.payment.dubboprovider.center.servcie.UserService;
//import org.apache.dubbo.config.ApplicationConfig;
//import org.apache.dubbo.config.ReferenceConfig;
//import org.apache.dubbo.config.RegistryConfig;
//import org.apache.dubbo.rpc.RpcContext;
//import org.apache.dubbo.rpc.protocol.dubbo.FutureAdapter;
//
///**
// * dubbo 2.7.0之前的版本异步调用方式2
// */
//public class APiAsyncConsumerForCallBack {
//	private static String applicationName = "payment-dubboConsumerTest-provider";
//
//	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";
//
//
//	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		// 1.创建引用实例，并设置属性
//		ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
//		referenceConfig.setApplication(new ApplicationConfig(applicationName));
//		referenceConfig.setRegistry(new RegistryConfig(registryAddress));
//		referenceConfig.setInterface(UserService.class);
//		referenceConfig.setTimeout(5000);
//		referenceConfig.setVersion("1.0.0");
//		referenceConfig.setGroup("dubbo");
//
//		// 2. 设置为异步
//		referenceConfig.setAsync(true);
//
//		// 3. 直接返回null
//		UserService userService = referenceConfig.get();
//		System.out.println(userService.sayHello("world"));
//
//		// 4.异步执行回调
//		((FutureAdapter) RpcContext.getContext().getFuture()).getFuture().setCallback(new ResponseCallback() {
//
//			@Override
//			public void done(Object response) {
//				System.out.println("result:" + response);
//			}
//
//			@Override
//			public void caught(Throwable exception) {
//				System.out.println("error:" + exception.getLocalizedMessage());
//			}
//		});
//
//		Thread.currentThread().join();
//	}
//}