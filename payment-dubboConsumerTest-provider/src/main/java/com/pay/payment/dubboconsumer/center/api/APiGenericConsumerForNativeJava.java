package com.pay.payment.dubboconsumer.center.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.io.UnsafeByteArrayInputStream;
import org.apache.dubbo.common.io.UnsafeByteArrayOutputStream;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.Constants;
import org.apache.dubbo.rpc.service.GenericService;


public class APiGenericConsumerForNativeJava {

	private static String applicationName = "payment-dubboConsumerTest-provider";

	private static String registryAddress = "zookeeper://10.22.31.103:2181?backup=10.22.31.116:2181,10.22.31.123:2181";


	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// 1.泛型参数固定为GenericService
		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(new ApplicationConfig(applicationName));
		referenceConfig.setRegistry(new RegistryConfig(registryAddress));

//		referenceConfig.setVersion("1.0.0");
//		referenceConfig.setGroup("dubbo");

		// 2. 设置为泛化引用，并且泛化类型为nativejava
		referenceConfig.setInterface("com.opay.payment.user.provider.facade.UserFacade");
		referenceConfig.setGeneric("nativejava");

		// 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
		GenericService greetingService = referenceConfig.get();
		UnsafeByteArrayOutputStream out = new UnsafeByteArrayOutputStream();

		// 4.泛型调用， 需要把参数使用Java序列化为二进制
		ExtensionLoader.getExtensionLoader(Serialization.class)
				.getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA).serialize(null, out).writeObject("156619112866619564");

		Object result = greetingService.$invoke("getUserByUserId", new String[] { "java.lang.String" },
				new Object[] { out.toByteArray() });

		// 5.打印结果，需要把二进制结果使用Java反序列为对象
		UnsafeByteArrayInputStream in = new UnsafeByteArrayInputStream((byte[]) result);
		System.out.println(ExtensionLoader.getExtensionLoader(Serialization.class)
				.getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA).deserialize(null, in).readObject());

	}
}