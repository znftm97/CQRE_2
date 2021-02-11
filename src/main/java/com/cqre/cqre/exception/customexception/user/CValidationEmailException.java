package com.cqre.cqre.exception.customexception.user;

public class CValidationEmailException extends RuntimeException{

    public CValidationEmailException() {
    }

    public CValidationEmailException(String message) {
        super(message);
    }

    public CValidationEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
