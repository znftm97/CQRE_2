package com.cqre.cqre.exception.customexception.gallery;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CFileIsNotImageGalleryException extends BusinessException {
    public CFileIsNotImageGalleryException() { super(ExceptionStatus.FILE_IS_NOT_IMAGE_EXCEPTION); }
}
