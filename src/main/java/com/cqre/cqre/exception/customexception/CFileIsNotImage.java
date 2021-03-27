package com.cqre.cqre.exception.customexception;

public class CFileIsNotImage extends RuntimeException{
    public CFileIsNotImage() {
    }

    public CFileIsNotImage(String message) {
        super(message);
    }

    public CFileIsNotImage(String message, Throwable cause) {
        super(message, cause);
    }
}
