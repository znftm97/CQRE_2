package com.cqre.cqre.security;

import com.cqre.cqre.exception.customexception.user.CEmailTokenFalseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg= "default errorMsg";

        if(exception instanceof BadCredentialsException){
            errorLogging(exception);
            errorMsg = "Wrong Password";
        } else if (exception instanceof UsernameNotFoundException) {
            errorLogging(exception);
            errorMsg = "Wrong ID";
        } else if (exception instanceof AuthenticationServiceException) {
            errorLogging(exception);
            errorMsg = "Not Exist User";
        } else if (exception instanceof CEmailTokenFalseException) {
            errorLogging(exception);
            errorMsg = "Email Verification Required";
        }

        setDefaultFailureUrl("/user/sign?error=true&errorMsg=" + errorMsg);
        super.onAuthenticationFailure(request, response, exception);
    }

    private void errorLogging(Exception ex) {
        log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
                ex.getLocalizedMessage());
    }

}

