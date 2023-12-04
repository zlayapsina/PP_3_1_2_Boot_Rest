package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repo.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> getUsers() {
        return usersRepository.findAll();
    }
    @Transactional
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void removeUser(long id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public void editUser(long id, User updatedUser) {
        updatedUser.setId(id);
        usersRepository.save(updatedUser);
    }

    public User showId(long id) {
        Optional<User> showedPerson = usersRepository.findById(id);
        return showedPerson.orElse(null);
    }
}
