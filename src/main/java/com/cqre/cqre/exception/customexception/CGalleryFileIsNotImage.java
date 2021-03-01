package com.cqre.cqre.exception.customexception;

public class CGalleryFileIsNotImage extends RuntimeException{
    public CGalleryFileIsNotImage() {
    }

    public CGalleryFileIsNotImage(String message) {
        super(message);
    }

    public CGalleryFileIsNotImage(String message, Throwable cause) {
        super(message, cause);
    }
}
