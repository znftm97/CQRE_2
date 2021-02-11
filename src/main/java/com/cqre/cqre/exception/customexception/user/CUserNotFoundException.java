package com.cqre.cqre.exception.customexception.user;

public class CUserNotFoundException extends RuntimeException{
    public CUserNotFoundException() {
    }

    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
