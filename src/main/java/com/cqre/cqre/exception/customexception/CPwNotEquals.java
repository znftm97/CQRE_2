package com.cqre.cqre.exception.customexception;

public class CPwNotEquals extends RuntimeException{
    public CPwNotEquals() {
    }

    public CPwNotEquals(String message) {
        super(message);
    }

    public CPwNotEquals(String message, Throwable cause) {
        super(message, cause);
    }
}
