package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;

import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;



@Controller
public class UsersController {

    private final UserService userService;

    private final UserValidator userValidator;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;

        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/";
    }

    @GetMapping("/user")
    public String showUserInfo(ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((CustomUserDetails)principal).getUsername();
        model.addAttribute("user", userService.findUserByUsername(username));
        return "user";
    }

    @GetMapping(value = "/admin/users")
    public String printUser(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }


    @GetMapping(value = "/registration")
    public String showRegistrationForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user)") @Valid User user,
                               @RequestParam(required = false) List<Long> roles,
                               BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUserWithRole(user, roles);
        return "redirect:/login";
    }

    @GetMapping(value = "/admin/edit")
    public String editPage(@RequestParam("id") long id, ModelMap model) {
        model.addAttribute("user", userService.showId(id));
        return "edit";
    }

    @PatchMapping("/admin/edit")
    public String editUser(@ModelAttribute("user") @Valid User user, @RequestParam("id") long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.editUser(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin/users";
    }

}