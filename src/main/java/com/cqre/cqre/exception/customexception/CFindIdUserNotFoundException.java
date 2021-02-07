package com.cqre.cqre.exception.customexception;

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
