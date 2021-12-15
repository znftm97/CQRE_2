package com.cqre.cqre.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class homeController {
    @GetMapping({"/", "/home"})
    public String home(){
        log.info("=============connect user=============");
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
