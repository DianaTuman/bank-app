package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.dto.UserPasswordDTO;
import com.dianatuman.practicum.accounts.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("info")
    public UserDTO getUserInfo(@PathVariable String login) {
        return userService.getUser(login);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
    }

    @PutMapping
    public void editUser(@RequestBody UserDTO userDTO) {
        userService.editUser(userDTO);
    }

    @PutMapping("password")
    public void editPassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.editPassword(userPasswordDTO);
    }
}
