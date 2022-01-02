package com.cqre.cqre.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserAspect {

    @Pointcut("execution(* com.cqre.cqre.security.CUserDetailsService.loadUserByUsername(String))")
    private void login(){}

    @Pointcut("execution(* com.cqre.cqre.security.OAuth2.CustomOAuth2UserService.loadUser(..))")
    private void socialLogin(){}

    @Around("execution(* com.cqre.cqre.controller.homeController.home())")
    public Object connectLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("============= connect user =============");
        return joinPoint.proceed();
    }

    @Before("login() && args(loginId,..)")
    public void loginLog(String loginId){
        log.info("============= login user - " + loginId + " =============");
    }

    @Around("socialLogin() && args(req,..)")
    public Object socialLoginLog(ProceedingJoinPoint joinPoint, OAuth2UserRequest req) throws Throwable {
        log.info("============= social login - " + req.getClientRegistration().getRegistrationId() + " =============");
        return joinPoint.proceed();
    }

}
