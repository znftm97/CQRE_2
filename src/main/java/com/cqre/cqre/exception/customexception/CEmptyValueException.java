package com.cqre.cqre.exception.customexception;

public class CEmptyValueException extends RuntimeException{
    public CEmptyValueException() {
    }

    public CEmptyValueException(String message) {
        super(message);
    }

    public CEmptyValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
