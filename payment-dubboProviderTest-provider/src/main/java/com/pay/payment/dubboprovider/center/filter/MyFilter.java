package com.pay.payment.dubboprovider.center.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate("eeeeee")
public class MyFilter implements Filter  {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return null;
    }

    @Override
    public Result onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        return null;
    }
}
