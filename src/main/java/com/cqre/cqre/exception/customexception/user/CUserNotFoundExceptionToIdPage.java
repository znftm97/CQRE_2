package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserNotFoundExceptionToIdPage extends BusinessException {
    public CUserNotFoundExceptionToIdPage() {
        super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_ID_PAGE);
    }
}
