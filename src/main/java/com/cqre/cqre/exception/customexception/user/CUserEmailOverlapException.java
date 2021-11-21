package com.cqre.cqre.exception.customexception.user;

import com.cqre.cqre.exception.BusinessException;
import com.cqre.cqre.exception.ExceptionStatus;

public class CUserEmailOverlapException extends BusinessException {
    public CUserEmailOverlapException(){
        super(ExceptionStatus.OVERLAP_EMAIL);
    }

}
