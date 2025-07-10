package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser() {

    }

    @GetMapping("login")
    public Boolean getUser() {
        return false;
    }

}
