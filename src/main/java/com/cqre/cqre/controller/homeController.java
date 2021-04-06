package com.cqre.cqre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
    @GetMapping({"/", "/home"})
    public String home(){
        return "/home/home";
    }

    @GetMapping("/history")
    public String history(){
        return "/history/history";
    }

    @GetMapping("/authorityException")
    public String authority() {
        return "/exception/authorityException";
    }
}
