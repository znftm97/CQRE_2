package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserNotFoundExceptionToCouponPage extends BusinessException {
    public CUserNotFoundExceptionToCouponPage() { super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_COUPON_PAGE); }
}
