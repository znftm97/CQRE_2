package com.cqre.cqre.exception.customexception.item;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CFileIsNotImageItemException extends BusinessException {
    public CFileIsNotImageItemException() { super(ExceptionStatus.FILE_IS_NOT_IMAGE_EXCEPTION); }

}
