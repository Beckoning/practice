package com.pay.payment.center.dealMethod;

import java.io.Serializable;

public interface ConverFunction<T> extends Serializable {

    Object getter(T t);
}
