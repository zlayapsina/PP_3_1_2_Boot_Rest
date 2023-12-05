package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repo.UsersRepository;

import java.util.Optional;

@Service
public class UserValidateService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserValidateService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
