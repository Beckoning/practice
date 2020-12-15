package com.pay.payment.center.javaFeatures;

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}