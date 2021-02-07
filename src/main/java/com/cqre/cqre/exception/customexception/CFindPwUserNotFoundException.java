package com.cqre.cqre.exception.customexception;

public class CFindPwUserNotFoundException extends RuntimeException{
    public CFindPwUserNotFoundException() {
    }

    public CFindPwUserNotFoundException(String message) {
        super(message);
    }

    public CFindPwUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
