package com.dianatuman.practicum.bank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    //only login == principal
    @PostMapping("/{login}/editPassword")
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

    @PostMapping("/{login}/editUserAccounts")
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

    @PostMapping("/{login}/cash")
    public String cash() {
        //        	Параметры:
        //        		login - логин пользователя
        //        		currency - строка с валютой
        //        		value - сумма внесения/снятия
        //        		action - действие (enum PUT иди GET)
        //        	Возвращает:
        //        		редирект на "/main"
        return "redirect:./main";
    }

    @PostMapping("/{login}/transfer")
    public String transfer() {
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
