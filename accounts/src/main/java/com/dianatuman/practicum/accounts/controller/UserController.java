package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.entity.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users/{login}")
public class UserController {

    @DeleteMapping
    public void deleteUser() {
    }

    @PutMapping
    public void editUser() {
    }

    @PutMapping("password")
    public void editPassword() {
    }
}
