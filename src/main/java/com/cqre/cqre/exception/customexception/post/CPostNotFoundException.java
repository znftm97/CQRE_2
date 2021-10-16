package com.cqre.cqre.exception.customexception.post;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CPostNotFoundException extends BusinessException {
    public CPostNotFoundException() { super(ExceptionStatus.POST_NOT_FOUND_EXCEPTION); }

}
