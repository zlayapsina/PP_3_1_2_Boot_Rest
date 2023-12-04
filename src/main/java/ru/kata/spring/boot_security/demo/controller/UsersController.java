package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;


@Controller
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUser());
        return "user";
    }

    @GetMapping(value = "/users")
    public String printUser(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping(value = "/new")
    public String newUserPage(ModelMap model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/edit")
    public String editPage(@RequestParam("id") long id, ModelMap model) {
        model.addAttribute("user", userService.showId(id));
        return "/edit";
    }

    @PatchMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user, @RequestParam("id") long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        userService.editUser(id, user);
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.removeUser(id);
        return "redirect:/";
    }

}