package com.cqre.cqre.exception.customexception.common;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CFileIsNotImageException extends BusinessException {
    public CFileIsNotImageException() { super(ExceptionStatus.FILE_IS_NOT_IMAGE_EXCEPTION); }

}
