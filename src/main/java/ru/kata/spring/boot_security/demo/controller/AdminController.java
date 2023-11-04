package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    private static final String REDIRECT = "redirect:/admin";

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }


    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/user";
    }

    @GetMapping(value = "")
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/user/{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") Long id, Model roles) {
        roles.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            userService.update(user);
            return REDIRECT;
        }
    }

    @GetMapping("/new")
    public String getNewUserPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.findAll());
        return "new";
    }

    @PostMapping("/newuser")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new";
        }

        userService.save(user);
        return REDIRECT;
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return REDIRECT;
    }
}
