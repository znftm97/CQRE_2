package com.cqre.cqre.exceptionhandler;

import com.cqre.cqre.exception.customexception.user.CEmailTokenFalseException;
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
public class CLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg= "default errorMsg";

        if(exception instanceof BadCredentialsException){
            errorMsg = "password is incorrect. Please enter again.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMsg = "Id is incorrect. Please enter again.";
        } else if (exception instanceof AuthenticationServiceException) {
            errorMsg = "This user does not exist.";
        } else if (exception instanceof CEmailTokenFalseException) {
            errorMsg = "You have to get email verification.";
        }

        setDefaultFailureUrl("/user/sign?error=true&errorMsg=" + errorMsg);
        super.onAuthenticationFailure(request, response, exception);

    }
}

