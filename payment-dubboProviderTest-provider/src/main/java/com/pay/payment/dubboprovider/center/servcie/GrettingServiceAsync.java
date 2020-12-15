package com.pay.payment.dubboprovider.center.servcie;

import java.util.concurrent.CompletableFuture;
 
public interface GrettingServiceAsync {
	CompletableFuture<String> sayHello(String name);
}