package com.cqre.cqre.controller.user;

import com.cqre.cqre.dto.SignInDto;
import com.cqre.cqre.dto.SignUpDto;
import com.cqre.cqre.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/sign")
    public String signIn(Model model){
        model.addAttribute("SignInDto", new SignInDto());
        model.addAttribute("SignUpDto", new SignUpDto());
        return "/user/sign";
    }

    @PostMapping("/user/signUp")
    public String signUp(@ModelAttribute("SignUpDto") @Valid SignUpDto signUpDto, BindingResult result){
        if (result.hasErrors()){
            return "/user/sign";
        }

        userService.signUp(signUpDto);
        return "redirect:/home";
    }
}
