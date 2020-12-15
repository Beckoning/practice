package com.pay.payment.center.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WarnAspect {


    @Around("execution(public * com.pay.payment.center.springtest.*.*(..))")
    public Object warn(ProceedingJoinPoint joinPoint)throws Throwable{
        System.out.println("Before  warn");
        Object object = joinPoint.proceed();
        System.out.println("After   warn");
        return object;
    }
}
