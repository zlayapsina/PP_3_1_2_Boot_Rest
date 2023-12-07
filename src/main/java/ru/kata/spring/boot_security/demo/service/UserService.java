package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repo.RolesRepository;
import ru.kata.spring.boot_security.demo.repo.UsersRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    public void saveUserWithRole(User user, List<Long> roleIds) {
        Set<Role> roleSet = roleIds.stream()
                .map(roleId -> rolesRepository.findById(roleId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }



//    @Transactional(readOnly = true)
//    public void registerUser(User user) throws RuntimeException {
//        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }
//    }

    @Transactional
    public void removeUser(long id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public void editUser(long id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        usersRepository.save(updatedUser);
    }

    public User showId(long id) {
        Optional<User> showedPerson = usersRepository.findById(id);
        return showedPerson.orElse(null);
    }

    public User findUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElse(null);
    }
    public Optional<User> findUserByUsernameValidate(String username) {
        return usersRepository.findByUsername(username);
    }
}
