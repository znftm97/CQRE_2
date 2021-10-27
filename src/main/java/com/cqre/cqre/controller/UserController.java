package com.cqre.cqre.controller;

import com.cqre.cqre.dto.user.*;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.exception.customexception.user.CPwNotEqualsException;
import com.cqre.cqre.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /*sign 페이지*/
    @GetMapping("/users/sign")
    public String signIn(@RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "errorMsg", required = false) String errorMsg,
                         Model model){
        model.addAttribute("SignUpDto", new SignUpDto());
        model.addAttribute("error", error);
        model.addAttribute("errorMsg", errorMsg);
        return "/user/sign";
    }

    /*회원가입*/
    @PostMapping("/users")
    public String signUp(@ModelAttribute("SignUpDto") @Valid SignUpDto signUpDto, BindingResult result)
            throws UnsupportedEncodingException, MessagingException {
        if (result.hasErrors()){
            return "/user/sign";
        }

        userService.signUp(signUpDto);

        return "/user/validationEmail";
    }

    /*이메일 인증*/
    @GetMapping("/users/validation-email")
    public String validationEmail(@RequestParam String email, @RequestParam String emailCheckToken){
        userService.validationEmailToken(email, emailCheckToken);

        return "/user/validationSuccess";
    }

    /*로그아웃*/
    @PostMapping("/users/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }

        return "/user/sign";
    }

    /*이메일 재인증 페이지*/
    @GetMapping("/users/email-re-validation")
    public String GAuthenticationEmail(Model model){
        model.addAttribute("ValidationEmailReDto", new ValidationEmailReDto());
        return "/user/validationEmailRe";
    }

    /*이메일 재인증*/
    @PostMapping("/users/email-re-validation")
    public String PAuthenticationEmail(@ModelAttribute("AuthenticationEmailDto") ValidationEmailReDto dto)
            throws UnsupportedEncodingException, MessagingException{

        userService.emailSendRe(dto);
        return "/user/validationEmail";
    }

    /*ID 찾기 페이지*/
    @GetMapping("/users/find-id")
    public String findId(Model model){
        model.addAttribute("userDto", new UserDto());

        return "/user/findId";
    }

    /*ID 찾기*/
    @PostMapping("/users/find-id")
    public String PFindId(@ModelAttribute("userDto") UserDto userDto, Model model){

        String findLoginId = userService.findId(userDto);
        model.addAttribute("findLoginId", findLoginId);

        return "/user/findId";
    }

    /*비밀번호 찾기 페이지*/
    @GetMapping("/users/find-pw")
    public String findPw(Model model){

        model.addAttribute("userDto", new UserDto());

        return "/user/findPw";
    }

    /*비밀번호 찾기*/
    @PostMapping("/users/find-pw")
    public String PFindPw(@ModelAttribute("userDto") UserDto userDto, Model model) throws UnsupportedEncodingException, MessagingException {

        userService.emailSendPw(userDto);

        return "/user/validationEmail";
    }

    /*이메일 인증 메일에서 버튼 클릭 url 매핑*/
    @GetMapping("/users/update-password")
    public String updatePassword(@RequestParam String email, Model model){

        model.addAttribute("updatePasswordDto", new UpdatePasswordDto());
        model.addAttribute("email", email);
        return "/user/updatePassword";
    }

    /*비밀번호 변경*/
    @PatchMapping("/users/password")
    public String PUpdatePassword(@ModelAttribute("updatePasswordDto") @Valid UpdatePasswordDto updatePasswordDto, BindingResult result, Model model){

        if (result.hasErrors()){
            return "/user/updatePassword";
        }

        if (!updatePasswordDto.getUpdatePassword().equals(updatePasswordDto.getUpdatePasswordRe())) {
            model.addAttribute("notEquals", "notEquals");
            return "/user/updatePassword";
        }

        userService.updatePassword(updatePasswordDto);

        return "redirect:/users/sign";
    }

    /*회원 정보 페이지*/
    @GetMapping("/users/user-info")
    public String userInfo(Model model, @RequestParam(value = "notEquals", required = false) String notEquals){

        User loginUser = userService.getLoginUser();
        UserDto loginUserDto = modelMapper.map(loginUser, UserDto.class);
        model.addAttribute("loginUserDto", loginUserDto);
        model.addAttribute("userPwCheckDto", new UserPwCheckDto());
        model.addAttribute("notEquals", notEquals);

        return "/user/userInfo";
    }

    /*비밀번호 재 검증 및 각 페이지 매핑(회원정보 수정, 비밀번호 변경, 회원탈퇴)*/
    @PostMapping("/users/pw-check")
    public String userInfoEdit(@ModelAttribute("userPwCheckDto") UserPwCheckDto userPwCheckDto, Model model, HttpSession session){
        User loginUser = userService.getLoginUser();

        if (!passwordEncoder.matches(userPwCheckDto.getPassword(), loginUser.getPassword())) {
            throw new CPwNotEqualsException();
        }

        if (userPwCheckDto.getIdentifier() == 1) {
            model.addAttribute("userAddressDto", new UserAddressDto());
            return "/user/updateUserInfo";

        } else if(userPwCheckDto.getIdentifier() == 2){
            /*userService.removeUser();*/
            session.invalidate();
            //Todo 글, 댓글, 등 다 삭제하는 로직 추가해야함

            return "redirect:/home";
        }

        return "";
    }

    /*회원 정보 수정*/
    @PatchMapping("/users")
    public String PUserInfoEdit(@ModelAttribute("userAddressDto") UserAddressDto userAddressDto, HttpSession session){
        userService.updateUserInfo(userAddressDto);

        session.invalidate();
        return "redirect:/home";
    }

}
