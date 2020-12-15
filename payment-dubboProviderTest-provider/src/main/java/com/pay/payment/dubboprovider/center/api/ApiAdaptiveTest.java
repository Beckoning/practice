//package com.pay.payment.dubboprovider.center.api;
//
//import com.pay.payment.dubboprovider.center.servcie.GreetingService;
//import org.apache.dubbo.common.URL;
//import org.apache.dubbo.common.extension.ExtensionLoader;
//import org.apache.dubbo.rpc.Invocation;
//import org.apache.dubbo.rpc.Invoker;
//import org.apache.dubbo.rpc.Result;
//import org.apache.dubbo.rpc.RpcException;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ApiAdaptiveTest {
//
//    public static void main(String[] args) {
//
//         GreetingService greetingService = ExtensionLoader.getExtensionLoader(GreetingService.class).getAdaptiveExtension();
//
//        org.apache.dubbo.rpc.Invoker invoker = new Invoker() {
//            @Override
//            public Class getInterface() {
//                return null;
//            }
//
//            @Override
//            public Result invoke(Invocation invocation) throws RpcException {
//                return null;
//            }
//
//            @Override
//            public URL getUrl() {
//                Map<String,String> parm = new HashMap<String,String>();
//                parm.put("greeting.service","");
//                return new URL("","",20000,parm);
//            }
//
//            @Override
//            public boolean isAvailable() {
//                return false;
//            }
//
//            @Override
//            public void destroy() {
//
//            }
//        };
//        greetingService.sayHelloWithAdptive(invoker);
//    }
//}
