package com.cqre.cqre.exception.customexception.item;

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
