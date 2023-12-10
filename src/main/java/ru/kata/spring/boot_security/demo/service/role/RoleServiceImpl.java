package ru.kata.spring.boot_security.demo.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repo.RolesRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RolesRepository rolesRepository;

    @Autowired
    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

}
