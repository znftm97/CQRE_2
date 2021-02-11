package com.cqre.cqre.exception.customexception.post;

public class CPostNotFoundException extends RuntimeException{
    public CPostNotFoundException() {
    }

    public CPostNotFoundException(String message) {
        super(message);
    }

    public CPostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
