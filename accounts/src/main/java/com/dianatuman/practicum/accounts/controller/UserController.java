package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.dto.UserPasswordDTO;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{login}")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserPasswordDTO getUser(@PathVariable String login) {
        return userService.getUserPassword(login);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
    }

    @PutMapping
    public void editUser(@PathVariable UserDTO userDTO) {
        userService.editUser(userDTO);
    }

    @PutMapping("password")
    public void editPassword(@PathVariable UserPasswordDTO userPasswordDTO) {
        userService.editPassword(userPasswordDTO);
    }
}
