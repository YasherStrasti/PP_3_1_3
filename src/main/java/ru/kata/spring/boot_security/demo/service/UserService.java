package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User getUserById(Long id);
    boolean save(User user);
    void update(User user);
    void delete(Long id);
    List<User> getAllUsers();
}
