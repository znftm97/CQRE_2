package com.cqre.cqre.exception.customexception;

public class CDiscountRateExceededException extends RuntimeException{
    public CDiscountRateExceededException() {
    }

    public CDiscountRateExceededException(String message) {
        super(message);
    }

    public CDiscountRateExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
