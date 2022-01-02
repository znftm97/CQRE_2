package com.cqre.cqre.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class OrderAspect {

    @Pointcut("execution(* com.cqre.cqre.service.OrderService.createOrder(Long, int))")
    private void order(){}

    @Pointcut("execution(* com.cqre.cqre.service.OrderService.createOrderWithCoupon(Long, int, String))")
    private void orderWithCoupon(){}

    @Around("order() || orderWithCoupon()")
    public Object orderLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("============= order =============");
        return joinPoint.proceed();
    }

}