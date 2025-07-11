package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.service.CashService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user/{login}")
public class UserController {

    private final CashService cashService;

    public UserController(CashService cashService) {
        this.cashService = cashService;
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
                       @RequestParam String action, @RequestParam String currency) {
        boolean cash = cashService.cash(login, currency, action, value);
        //        	Параметры:
            //        		login - логин пользователя
            //        		currency - строка с валютой
            //        		value - сумма внесения/снятия
            //        		action - действие (enum PUT иди GET)
            //        	Возвращает:
            //        		редирект на "/main"
            return "redirect:./main";
    }

    @PostMapping("/transfer")
    public String transfer(@PathVariable String login, @RequestParam String from_currency,
                           @RequestParam Float value,
                           @RequestParam String to_login, @RequestParam String to_currency) {
        //        	Параметры:
        //        		login - логин пользователя
        //        		from_currency - строка с валютой счета, с которого переводятся деньги
        //        		to_currency - строка с валютой счета, на который переводятся деньги
        //        		value - сумма внесения/снятия
        //        		to_login - логин пользователя, которому переводятся деньги
        //        	Возвращает:
        //        		редирект на "/main"
        return "redirect:./main";
    }
}
