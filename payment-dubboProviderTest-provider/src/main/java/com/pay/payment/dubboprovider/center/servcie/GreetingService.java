package com.pay.payment.dubboprovider.center.servcie;


import com.pay.payment.dubboprovider.center.common.PoJo;
import com.pay.payment.dubboprovider.center.common.Result;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;


/**
 * 如果是dubboSpi 需要在接口添加@SPI注解
 *
 */
public interface GreetingService {


	String sayHello(String name);

	Result<String> testGeneric(PoJo poJo);

	String sayHello();


}
