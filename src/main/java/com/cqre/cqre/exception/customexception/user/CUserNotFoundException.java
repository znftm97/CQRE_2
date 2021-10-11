package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserNotFoundException extends BusinessException {
    public CUserNotFoundException() { super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION); }

}
