package com.cqre.cqre.exception.customexception.user;

public class CFindIdUserNotFoundException extends RuntimeException{
    public CFindIdUserNotFoundException() {
    }

    public CFindIdUserNotFoundException(String message) {
        super(message);
    }

    public CFindIdUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
