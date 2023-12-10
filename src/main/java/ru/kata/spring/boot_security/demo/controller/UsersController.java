package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/";
    }

    @GetMapping("/user")
    public String showUserInfo(ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("current_user", principal);
        return "main";
    }
}