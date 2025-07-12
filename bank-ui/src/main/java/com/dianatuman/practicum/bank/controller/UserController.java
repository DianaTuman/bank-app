package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.service.CashService;
import com.dianatuman.practicum.bank.service.TransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/user/{login}")
public class UserController {

    private final CashService cashService;

    private final TransferService transferService;

    public UserController(CashService cashService, TransferService transferService) {
        this.cashService = cashService;
        this.transferService = transferService;
    }

    //only login == principal
    @PostMapping("/editPassword")
    public String editPassword() {
        // POST "/user/{login}/editPassword" - эндпоинт смены пароля (записывает список ошибок, если есть, в passwordErrors)
        //        	Параметры:
        //        		login - логин пользователя
        //        		password - новый пароль
        //        		confirm_password - новый пароль второй раз
        //        	Возвращает:
        //        		редирект на "/main"
        return "redirect:./main";
    }

    @PostMapping("/editUserAccounts")
    public String editUserAccounts() {
        //Параметры:
        //        		login - логин пользователя
        //        		name - фамилия и имя пользователя
        //        		birthdate - дата рождения пользователя (LocalDate)
        //        		account - список строк с валютами пользователя, для которых у него есть счета
        //        	Возвращает:
        //        		редирект на "/main"
        return "redirect:./main";
    }

    @PostMapping("/cash")
    public String cash(@PathVariable String login, @RequestParam Float value,
                       @RequestParam String action, @RequestParam String currency) throws JsonProcessingException {
        boolean cash = cashService.cash(login, currency, action, value);
        return "redirect:./main";
    }

    @PostMapping("/transfer")
    public String transfer(@PathVariable String login, @RequestParam String from_currency,
                           @RequestParam Float value,
                           @RequestParam String to_login, @RequestParam String to_currency) throws JsonProcessingException {
        boolean transfer = transferService.transfer(login, from_currency, value, to_login, to_currency);
        return "redirect:./main";
    }
}
