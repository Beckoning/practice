package com.pay.payment.dubboprovider.center.servcie;

import com.pay.payment.dubboprovider.center.common.PoJo;
import com.pay.payment.dubboprovider.center.common.Result;
import org.apache.dubbo.common.extension.SPI;

@SPI("spiService")
public interface SpiService {


    String sayHello(String name);

    Result<String> testGeneric(PoJo poJo);

    String sayHello();

    String sayHelloWithAdptive(org.apache.dubbo.rpc.Invoker arg0);

}
