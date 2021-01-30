package com.cqre.cqre.controller.user;

import com.cqre.cqre.dto.SignInDto;
import com.cqre.cqre.dto.SignUpDto;
import com.cqre.cqre.repository.user.UserRepository;
import com.cqre.cqre.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /*sign 페이지*/
    @GetMapping("/user/sign")
    public String signIn(Model model){
        model.addAttribute("SignInDto", new SignInDto());
        model.addAttribute("SignUpDto", new SignUpDto());
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

        return "/user/validationEmail";
    }

    /*이메일 인증*/
    @GetMapping("/user/validationEmail")
    public String validationEmail(@RequestParam String email, @RequestParam String emailCheckToken){
        userService.validationEmailToken(email, emailCheckToken);

        return "/user/validationSuccess";
    }
}
