package com.pay.payment.dubboprovider.center.api;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import com.pay.payment.dubboprovider.center.servcie.SpiService;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class ApiDubboSpiTest {


    public static void main(String[] args) {

        ExtensionLoader<SpiService> extensionLoader =
                ExtensionLoader.getExtensionLoader(SpiService.class);
        SpiService spiService = extensionLoader.getExtension("spiService");
        spiService.sayHello();
        SpiService spiTwoService = extensionLoader.getExtension("spiTwoService");
        spiTwoService.sayHello();

    }

}
