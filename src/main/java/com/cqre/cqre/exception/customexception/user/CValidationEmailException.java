package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CValidationEmailException extends BusinessException {
    public CValidationEmailException() { super(ExceptionStatus.VALIDATION_EMAIL_EXCEPTION); }

}
