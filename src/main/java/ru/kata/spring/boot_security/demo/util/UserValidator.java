package ru.kata.spring.boot_security.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserValidator.class);
    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> existingUser = userService.findUserByUsernameValidate(user.getUsername());
        if (existingUser.isPresent()) {
            logger.info("Попытка регистрации с существующим username: {}", user.getUsername());
            errors.rejectValue("username", "user.username.exists", "Пользователь с таким логином уже существует");
        }
    }
}
