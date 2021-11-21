package com.cqre.cqre.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

    // common - 1000
    FILE_IS_NOT_IMAGE_EXCEPTION(1000, "이미지 파일 형태가 아니거나 존재하지 않습니다.", BAD_REQUEST),

    // user - 2000
    USER_NOT_FOUND_EXCEPTION(2000, "유저가 존재하지 않습니다.", NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION_TO_ID_PAGE(2001, "유저가 존재하지 않습니다.", NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION_TO_PW_PAGE(2002, "유저가 존재하지 않습니다.", NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION_TO_EMAIL_PAGE(2003, "유저가 존재하지 않습니다.", NOT_FOUND),
    USER_NOT_FOUND_EXCEPTION_TO_COUPON_PAGE(2004, "유저가 존재하지 않습니다.", NOT_FOUND),
    PW_NOT_EQUALS_EXCEPTION(2005, "비밀번호가 일치하지 않습니다.", BAD_REQUEST),
    VALIDATION_EMAIL_EXCEPTION(2006, "이메일 토큰 값이 일치하지 않습니다..", UNAUTHORIZED),
    OVERLAP_EMAIL(2007, "이미 존재하는 이메일입니다.", CONFLICT),
    OVERLAP_LOGIN_ID(2008, "이미 존재하는 아이디입니다.", CONFLICT),

    // post - 3000
    POST_NOT_FOUND_EXCEPTION(3000, "글을 찾을 수 없습니다.", NOT_FOUND),

    // item - 4000
    NOT_ENOUGH_STOCK_EXCEPTION(4000, "재고가 부족합니다.", BAD_REQUEST);





    private final int status;
    private final String message;
    private final HttpStatus httpStatus;
}
