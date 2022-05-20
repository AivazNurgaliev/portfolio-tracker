package com.ourproject.portfoliotracker.web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    String redirect(Authentication authentication) {
        if (authentication == null) {
            return "redirect:overview";
        } else {
            return "redirect:home";
        }
    }

    @GetMapping("/overview")
    String overview() {
        return "overview";
    }

    @GetMapping("/home")
    String home() {
        return "home";
    }
    
    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/restore")
    String restore() {
        return "restore";
    }

    @GetMapping("/register")
    String register() {
        return "register";
    }
}
