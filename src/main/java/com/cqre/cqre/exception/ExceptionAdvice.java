package com.cqre.cqre.exception;

import com.cqre.cqre.exception.customexception.common.CFileIsNotImageException;
import com.cqre.cqre.exception.customexception.item.CNotEnoughStockException;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.exception.customexception.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(CUserNotFoundException.class)
    private String userNotFoundException(Model model) {
        return "/userNotFoundException";
    }

    @ExceptionHandler(CUserNotFoundExceptionToIdPage.class)
    private String userNotFoundExceptionToIdPage(Model model) {
        model.addAttribute("notExist", "notExist");
        return "/user/findId";
    }

    @ExceptionHandler(CUserNotFoundExceptionToPwPage.class)
    private String userNotFoundExceptionToPwPage(Model model) {
        model.addAttribute("notExist", "notExist");
        return "/user/findPw";

    }

    @ExceptionHandler(CUserNotFoundExceptionToEmailPage.class)
    private String userNotFoundExceptiontoEmailpage(Model model) {
        model.addAttribute("notExist", "notExist");
        return "/user/validationEmailRe";

    }

    @ExceptionHandler(CUserNotFoundExceptionToCouponPage.class)
    private String userNotFoundExceptionToCouponPage(Model model) {
        model.addAttribute("notExist", "notExist");
        return "/couponList";

    }

    @ExceptionHandler(CPwNotEqualsException.class)
    private String pwNotEquals(Model model){
        model.addAttribute("notEquals", "true");
        return "redirect:/user/userInfo";
    }

    @ExceptionHandler(CPostNotFoundException.class)
    private String postNotFound(){
        return "/post/postNotFoundException";
    }

    @ExceptionHandler(ClassCastException.class)
    private String userNotLogin(){
        return "/user/exception/userNotFoundException";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private String emailOverlapCheck(Model model){
        model.addAttribute("emailOverlap", "true");
        return "/home/home";
    }

    @ExceptionHandler(CFileIsNotImageException.class)
    private String notImageFile(Model model){
        model.addAttribute("notImageFile", "true");
        return "/gallery/createGallery";
    }

    @ExceptionHandler(CNotEnoughStockException.class)
    private String notEnoughStock(Model model){
        model.addAttribute("notEnoughStock", "true");
        return "redirect:/shop";
    }

//    @ExceptionHandler(CDiscountRateExceededException.class)
//    protected String discountRateExceeded(Model model){
//        model.addAttribute("discountRateExceeded", "true");
//        return "/coupon/createCoupon";
//    }

//    @ExceptionHandler(CEmptyValueException.class)
//    protected String emptyValueCouponName(Model model){
//        model.addAttribute("emptyValueCouponName", "true");
//        return "/coupon/createCoupon";
//    }
}
