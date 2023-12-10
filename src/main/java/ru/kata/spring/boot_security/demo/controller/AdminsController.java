package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping
    public String printUser(ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("current_user", principal);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "main";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                               @RequestParam List<Long> roles, ModelMap model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "registration";
        }
        userService.saveUserWithRole(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, ModelMap model) {
        model.addAttribute("user", userService.showId(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam("id") long id,  @RequestParam Set<Long> roles, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "edit";
        }
        userService.editUser(id, user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
