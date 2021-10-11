package com.cqre.cqre.exception;

public class BusinessException extends RuntimeException{
    private final ExceptionStatus responseStatus;

    public BusinessException(ExceptionStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
