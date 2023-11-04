package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;


public interface RoleService {

    Role showRoleById(Long id);
    void save(Role role);
    void deleteById(Long id);
    List<Role> findAll();
}
