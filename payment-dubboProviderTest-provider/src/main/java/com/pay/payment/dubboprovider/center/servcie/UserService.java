package com.pay.payment.dubboprovider.center.servcie;

import com.pay.payment.dubboprovider.center.common.PoJo;
import com.pay.payment.dubboprovider.center.common.Result;

public interface UserService {


    String sayHello(String name);

    Result<String> testGeneric(PoJo poJo);

    String sayHello();
}
