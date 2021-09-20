package com.cqre.cqre.exception.customexception;

public class CNotEnoughStockException extends RuntimeException{

    public CNotEnoughStockException() {
        super();
    }

    public CNotEnoughStockException(String message) {
        super(message);
    }

    public CNotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
