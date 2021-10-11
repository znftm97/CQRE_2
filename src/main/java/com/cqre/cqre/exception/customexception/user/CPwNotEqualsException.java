package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CPwNotEqualsException extends BusinessException {
    public CPwNotEqualsException() { super(ExceptionStatus.PW_NOT_EQUALS_EXCEPTION); }

}
