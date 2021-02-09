package com.cqre.cqre.exception;

import com.cqre.cqre.exception.customexception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(CValidationEmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult validationEmailFail(){
        return responseService.getFailResultValidationEmail();
    }

    @ExceptionHandler(CFindIdUserNotFoundException.class)
    protected String findIdEntityNotFound(Model model) {
        model.addAttribute("notExist", "notExist");
        return "/user/findId";
    }

    @ExceptionHandler(CFindPwUserNotFoundException.class)
    protected String findPwEntityNotFound(Model model) {
        model.addAttribute("notExist", "notExist");
        return "/user/findPw";
    }

    @ExceptionHandler(CPwNotEquals.class)
    protected String pwNotEquals(Model model){
        model.addAttribute("notEquals", "notEquals");
        return "redirect:/user/userInfo";
    }

    @ExceptionHandler(CUserNotFoundException.class)
    protected String userNotFound(){
        return "/user/userNotFoundException";
    }
}
