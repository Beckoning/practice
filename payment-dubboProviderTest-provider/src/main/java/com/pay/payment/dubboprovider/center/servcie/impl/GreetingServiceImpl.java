package com.pay.payment.dubboprovider.center.servcie.impl;

import java.io.IOException;


import com.pay.payment.dubboprovider.center.common.PoJo;
import com.pay.payment.dubboprovider.center.common.Result;
import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;


public class GreetingServiceImpl implements GreetingService {

	@Override
	public String sayHello(String name) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Hello " + name + " " + RpcContext.getContext().getAttachment("company");
	}

	@Override
	public Result<String> testGeneric(PoJo poJo) {
		Result<String> result = new Result<String>();
		result.setSucess(true);
		try {
			result.setData(JSON.json(poJo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String sayHello() {

		System.out.println("Hello "  + " ");
		return "Hello ";

	}

}