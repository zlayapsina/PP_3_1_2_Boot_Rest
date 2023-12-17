package ru.kata.spring.boot_security.demo.service.user;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    void saveUserWithRole(User user);

    void removeUser(long id);

    void editUser(User updatedUser);

    User showId(long id);

    User findUserByUsername(String username);

    Optional<User> findUserByUsernameValidate(String username);

}
