package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        User user = userService.showId(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID " + id);
        }
        return user;
    }

    @PostMapping("/users")
    public List<User> addNewUser(@RequestBody User user) {
        userService.saveUserWithRole(user);
        return getAllUsers();
    }

    @PutMapping("/users")
    public List<User> updateUser(@RequestBody User user) {
        userService.editUser(user);
        return getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public List<User> deleteUser(@PathVariable long id) {
        User user = userService.showId(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID " + id);
        }
        userService.removeUser(id);
        return getAllUsers();

    }
}
