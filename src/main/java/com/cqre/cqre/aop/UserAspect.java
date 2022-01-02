package com.cqre.cqre.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserAspect {

    @Pointcut("execution(* com.cqre.cqre.security.CUserDetailsService.loadUserByUsername(String))")
    private void login(){}

    @Around("execution(* com.cqre.cqre.controller.homeController.home())")
    public Object connectLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("============= connect user =============");
        return joinPoint.proceed();
    }

    @Before("login() && args(loginId,..)")
    public void loginLog2(String loginId){
        log.info("============= login user - " + loginId + " =============");
    }

}
