package ru.kata.spring.boot_security.demo.service.user;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> getUsers();

    void saveUserWithRole(User user, List<Long> roleIds);

    void removeUser(long id);

    void editUser(long id, User updatedUser, Set<Long> roleIds);

    User showId(long id);

    User findUserByUsername(String username);

    Optional<User> findUserByUsernameValidate(String username);

}
