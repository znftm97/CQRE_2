package com.cqre.cqre.exception.customexception.user;

import org.springframework.security.core.AuthenticationException;

public class CEmailTokenFalseException extends AuthenticationException {
    public CEmailTokenFalseException(String msg) {
        super(msg);
    }

}
