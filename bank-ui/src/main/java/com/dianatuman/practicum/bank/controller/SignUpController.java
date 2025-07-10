package com.dianatuman.practicum.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/signup")
public class SignUpController {

    @GetMapping
    public String signup() {
        return "signup.html";
    }


    @PostMapping
    public String register() {
        //POST "/signup" - эндпоинт регистрации нового пользователя
        //       		Параметры:
        //       			login - логин пользователя
        //       			password - пароль пользователя
        //       			confirm_password - пароль пользователя второй раз
        //       			name - фамилия и имя пользователя
        //       			birthdate - дата рождения пользователя (LocalDate)
        //       			@RequestParam("login") String login,
        //		Возвращает:
        //            		редирект на "/main"
        //            	В случае ошибок возвращает:
        //            		шаблон "signup.html"
        //            		используется модель для заполнения шаблона:
        //            			"login" - строка с логином пользователя
        //            			"name" - строка с фамилией и именем пользователя
        //            			"birthdate" - LocalDate с датой рождения пользователя
        //            			"errors" - список ошибок при регистрации
        return "redirect:./main";
    }

}
