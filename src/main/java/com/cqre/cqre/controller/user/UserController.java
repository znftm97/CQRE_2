package com.cqre.cqre.controller.user;

import com.cqre.cqre.dto.ValidationEmailReDto;
import com.cqre.cqre.dto.SignUpDto;
import com.cqre.cqre.dto.FindIdPwDto;
import com.cqre.cqre.repository.user.UserRepository;
import com.cqre.cqre.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /*sign 페이지*/
    @GetMapping("/user/sign")
    public String signIn(@RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "errorMsg", required = false) String errorMsg,
                         Model model){
        model.addAttribute("SignUpDto", new SignUpDto());
        model.addAttribute("error", error);
        model.addAttribute("errorMsg", errorMsg);
        return "/user/sign";
    }

    /*회원가입*/
    @PostMapping("/user/signUp")
    public String signUp(@ModelAttribute("SignUpDto") @Valid SignUpDto signUpDto, BindingResult result)
            throws UnsupportedEncodingException, MessagingException {
        if (result.hasErrors()){
            return "/user/sign";
        }

        userService.signUp(signUpDto);

        /*return "/home/home";*/

        return "/user/validationEmail";
    }

    /*이메일 인증*/
    @GetMapping("/user/validationEmail")
    public String validationEmail(@RequestParam String email, @RequestParam String emailCheckToken){
        userService.validationEmailToken(email, emailCheckToken);

        return "/user/validationSuccess";
    }

    /*로그아웃*/
    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }

        return "/user/sign";
    }

    /*이메일 재인증 페이지*/
    @GetMapping("/user/validationEmailRe")
    public String GAuthenticationEmail(Model model){
        model.addAttribute("ValidationEmailReDto", new ValidationEmailReDto());
        return "/user/validationEmailRe";
    }

    /*이메일 재인증*/
    @PostMapping("/user/validationEmailRe")
    public String PAuthenticationEmail(@ModelAttribute("AuthenticationEmailDto") ValidationEmailReDto dto)
            throws UnsupportedEncodingException, MessagingException{

        userService.emailSendRe(dto);
        return "/user/validationEmail";
    }

    /*ID 찾기 페이지*/
    @GetMapping("/user/findId")
    public String findId(Model model){
        model.addAttribute("findIdPwDto", new FindIdPwDto());

        return "/user/findId";
    }

    /*ID 찾기*/
    @PostMapping("/user/findId")
    public String PFindId(@ModelAttribute("findIdPwDto") FindIdPwDto findIdPwDto, Model model){

        String findLoginId = userService.findId(findIdPwDto);
        model.addAttribute("findLoginId", findLoginId);

        return "/user/findId";
    }
}
