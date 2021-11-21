package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserLoginIdOverlapException extends BusinessException {
    public CUserLoginIdOverlapException(){
        super(ExceptionStatus.OVERLAP_LOGIN_ID);
    }

}
