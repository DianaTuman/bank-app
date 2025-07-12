package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.dto.UserDTO;
import com.dianatuman.practicum.bank.service.AccountsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller("/")
public class HomeController {

    private final AccountsService accountsService;

    public HomeController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public String homePage() {
        return "redirect:./main";
    }

    @GetMapping("/main")
    public String mainPage(Model model, Principal principal) {
        UserDTO userDTO = accountsService.getUserByName(principal.getName());
        model.addAttribute("login", userDTO.getLogin());
        model.addAttribute("name", userDTO.getName());
        model.addAttribute("birthdate", userDTO.getBirthdate());
        model.addAttribute("accounts", userDTO.getAccounts());
        model.addAttribute("users", accountsService.getAllUsers());

        //            			"currency" - список всех доступных валют:
        //            				"title" - название валюты
        //            				"name()" - код валюты
        //            			"passwordErrors" - список ошибок при смене пароля (null, если не выполнялась смена пароля)
        //            			"userAccountsErrors" - список ошибок при редактировании настроек аккаунта (null, если не выполнялось редактирование)
        //            			"cashErrors" - список ошибок при внесении/снятии денег (null, если не выполнялось внесение/снятие)
        //            			"transferErrors" - список ошибок при переводе между своими счетами (null, если не выполнялся перевод)
        //            			"transferOtherErrors" - список ошибок при переводе на счет другого пользователя (null, если не выполнялся перевод)
        return "main.html";
    }
}
