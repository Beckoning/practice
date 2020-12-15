package com.pay.payment.center.dealMethod;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class ConvertRequest {

    public <T> ConvertRequest eq(ConverFunction<T> column, Object value){
        String columnName = getFieldName(column);

        return this;
    }

    public String getFieldName(ConverFunction func){
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda)method.invoke(func);
            String getter = serializedLambda.getImplMethodName();
            return resolveFieldName(getter);
        } catch (ReflectiveOperationException var4) {
            throw new RuntimeException(var4);
        }

    }


    private static String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith("is")) {
            getMethodName = getMethodName.substring(2);
        }

        return firstToLowerCase(getMethodName);
    }

    private static String firstToLowerCase(String param) {
        return StringUtils.isBlank(param) ? "" : param.substring(0, 1).toLowerCase() + param.substring(1);
    }
}


