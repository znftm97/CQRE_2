package com.cqre.cqre.exception;

import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public enum CommonResponse {
        VALIDATIONEMAILFAIL(-1, "이메일 인증 실패");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    //로그인 실패 결과 처리
    public CommonResult getFailResultValidationEmail(){
        CommonResult result = new CommonResult();
        result.setCode(CommonResponse.VALIDATIONEMAILFAIL.getCode());
        result.setMsg(CommonResponse.VALIDATIONEMAILFAIL.getMsg());
        return result;
    }
}
