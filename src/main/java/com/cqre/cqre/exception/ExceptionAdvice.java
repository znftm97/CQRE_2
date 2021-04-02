package com.cqre.cqre.exception;

import com.cqre.cqre.exception.customexception.CFileIsNotImage;
import com.cqre.cqre.exception.customexception.CNotEnoughStockException;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.exception.customexception.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
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
        return "/user/exception/userNotFoundException";
    }

    @ExceptionHandler(CPostNotFoundException.class)
    protected String postNotFound(){
        return "/post/postNotFoundException";
    }

    @ExceptionHandler(ClassCastException.class)
    protected String userNotLogin(){
        return "/user/exception/userNotFoundException";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected String emailOverlapCheck(Model model){
        model.addAttribute("emailOverlap", "true");
        return "/home/home";
    }

    @ExceptionHandler(CFileIsNotImage.class)
    protected String notImageFile(Model model){
        model.addAttribute("notImageFile", "true");
        return "/gallery/createGallery";
    }

    @ExceptionHandler(CNotEnoughStockException.class)
    protected String notEnoughStock(Model model){
        model.addAttribute("notEnoughStock", "true");
        return "redirect:/shop";
    }
}
