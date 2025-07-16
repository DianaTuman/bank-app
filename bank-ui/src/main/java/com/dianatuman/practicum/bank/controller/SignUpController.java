package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.service.AccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final AccountsService accountsService;

    public SignUpController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public String signup() {
        return "signup.html";
    }


    @PostMapping
    public String register(@RequestParam("login") String login, @RequestParam("password") String password,
                           @RequestParam("confirm_password") String confirmPassword, @RequestParam("name") String name,
                           @RequestParam("birthdate") LocalDate birthdate, Model model) throws JsonProcessingException {
        List<String> errors = new ArrayList<>();
        if (password.equals(confirmPassword)) {
            String s = accountsService.registerUser(login, password, name, birthdate);
            if (!s.isEmpty()) {
                errors.add(s);
            }
        } else {
            errors.add("Passwords do not match");
        }

        if (errors.isEmpty()) {
            return "redirect:./main";
        } else {
            model.addAttribute("login", login);
            model.addAttribute("name", name);
            model.addAttribute("birthdate", birthdate);
            model.addAttribute("errors", errors);
            return "signup.html";
        }
    }

}
