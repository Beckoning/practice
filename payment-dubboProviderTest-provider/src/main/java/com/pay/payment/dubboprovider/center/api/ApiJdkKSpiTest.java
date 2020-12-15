package com.pay.payment.dubboprovider.center.api;

import com.pay.payment.dubboprovider.center.servcie.GreetingService;
import com.pay.payment.dubboprovider.center.servcie.SpiService;

import java.util.ServiceLoader;

public class ApiJdkKSpiTest {

    public static void main(String[] args) {

        ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(SpiService::sayHello);
    }
}
