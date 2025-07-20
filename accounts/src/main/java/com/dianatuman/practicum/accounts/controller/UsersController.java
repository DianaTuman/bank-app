package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.dto.UsersListDTO;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UsersListDTO getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        if (userService.userExists(user.getLogin())) {
            throw new DuplicateKeyException("User with this login already exists!");
        }
        userService.createUser(user);
    }

}
