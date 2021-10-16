package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserNotFoundExceptionToEmailPage extends BusinessException {
    public CUserNotFoundExceptionToEmailPage() { super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_EMAIL_PAGE); }

}
