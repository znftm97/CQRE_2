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
    private String userNotFoundException(Model model, CUserNotFoundException exception) {
        errorLogging(exception);
        return "/userNotFoundException";
    }

    @ExceptionHandler(CUserNotFoundExceptionToIdPage.class)
    private String userNotFoundExceptionToIdPage(Model model, CUserNotFoundExceptionToIdPage exception) {
        errorLogging(exception);
        model.addAttribute("notExist", "notExist");
        return "/user/findId";
    }

    @ExceptionHandler(CUserNotFoundExceptionToPwPage.class)
    private String userNotFoundExceptionToPwPage(Model model, CUserNotFoundExceptionToPwPage exception) {
        errorLogging(exception);
        model.addAttribute("notExist", "notExist");
        return "/user/findPw";

    }

    @ExceptionHandler(CUserNotFoundExceptionToEmailPage.class)
    private String userNotFoundExceptiontoEmailpage(Model model, CUserNotFoundExceptionToEmailPage exception) {
        errorLogging(exception);
        model.addAttribute("notExist", "notExist");
        return "/user/validationEmailRe";

    }

    @ExceptionHandler(CUserNotFoundExceptionToCouponPage.class)
    private String userNotFoundExceptionToCouponPage(Model model, CUserNotFoundExceptionToCouponPage exception) {
        errorLogging(exception);
        model.addAttribute("notExist", "notExist");
        return "/couponList";

    }

    @ExceptionHandler(CPwNotEqualsException.class)
    private String pwNotEquals(Model model, CPwNotEqualsException exception){
        errorLogging(exception);
        model.addAttribute("notEquals", "true");
        return "redirect:/users/user-info";
    }

    @ExceptionHandler(CPostNotFoundException.class)
    private String postNotFound(CPostNotFoundException exception){
        errorLogging(exception);
        return "/post/postNotFoundException";
    }

    @ExceptionHandler(ClassCastException.class)
    private String userNotLogin(ClassCastException exception){
        errorLogging(exception);
        return "/user/exception/userNotFoundException";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private String emailOverlapCheck(Model model, DataIntegrityViolationException exception){
        errorLogging(exception);
        model.addAttribute("emailOverlap", "true");
        return "/home/home";
    }

    @ExceptionHandler(CFileIsNotImageException.class)
    private String notImageFile(Model model, CFileIsNotImageException exception){
        errorLogging(exception);
        model.addAttribute("notImageFile", "true");
        return "/gallery/createGallery";
    }

    @ExceptionHandler(CNotEnoughStockException.class)
    private String notEnoughStock(Model model, CNotEnoughStockException exception){
        errorLogging(exception);
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

    private void errorLogging(Exception ex) {
        log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
                ex.getLocalizedMessage());
    }
}
