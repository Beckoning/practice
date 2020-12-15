package com.pay.payment.center.javaFeatures;

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);

}