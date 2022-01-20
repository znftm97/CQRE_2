package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CAnonymousUserException extends BusinessException {
    public CAnonymousUserException() {
        super(ExceptionStatus.ANONYMOUS_USER_BLOCK_EXCEPTION);
    }
}
