package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserNotFoundExceptionToPwPage extends BusinessException {
    public CUserNotFoundExceptionToPwPage() { super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_PW_PAGE); }

}
