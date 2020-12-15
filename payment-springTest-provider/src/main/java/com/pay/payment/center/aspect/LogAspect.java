package com.pay.payment.center.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Around("execution(public * com.pay.payment.center.springtest.*.*(..))")
    public Object logPrint(ProceedingJoinPoint joinPoint)throws Throwable{
        joinPoint.getArgs();
        System.out.println("log Before");
        Object object = joinPoint.proceed();
        System.out.println("log After");

        return object;
    }

//    @Around("execution(public * com.pay.payment.center.*.*.*(..))")
//    public Object logPrintTest(ProceedingJoinPoint joinPoint)throws Throwable{
//        joinPoint.getArgs();
//        System.out.println("log Before");
//        Object object = joinPoint.proceed();
//        System.out.println("log After");
//
//        return object;
//    }



}
