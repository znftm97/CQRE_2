package com.cqre.cqre.exception;

import com.cqre.cqre.exception.customexception.coupon.CDiscountRateExceededException;
import com.cqre.cqre.exception.customexception.coupon.CEmptyValueException;
import com.cqre.cqre.exception.customexception.common.CFileIsNotImage;
import com.cqre.cqre.exception.customexception.item.CNotEnoughStockException;
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
@Slf4j
public class ExceptionAdvice {

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
        model.addAttribute("notEquals", "true");
        return "redirect:/user/userInfo";
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

    @ExceptionHandler(CDiscountRateExceededException.class)
    protected String discountRateExceeded(Model model){
        model.addAttribute("discountRateExceeded", "true");
        return "/coupon/createCoupon";
    }

    @ExceptionHandler(CEmptyValueException.class)
    protected String emptyValueCouponName(Model model){
        model.addAttribute("emptyValueCouponName", "true");
        return "/coupon/createCoupon";
    }
}
