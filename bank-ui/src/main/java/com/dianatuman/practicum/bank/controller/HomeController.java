package com.dianatuman.practicum.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class HomeController {

    @GetMapping
    public String homePage() {
        return "redirect:./main";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {

//        model.addAttribute("user", );

        //             		шаблон "main.html"
        //            		используется модель для заполнения шаблона:
        //            			"login" - строка с логином пользователя
        //            			"name" - строка с фамилией и именем пользователя
        //            			"birthdate" - LocalDate с датой рождения пользователя
        //            			"accounts" - список всех зарегистрированных пользователей:
        //            				"currency" - enum валюта:
        //            					"title" - название валюты
        //            					"name()" - код валюты
        //            				"value" - сумма на счету пользователя в этой валюте
        //            				"exists" - true, если у пользователя есть счет в этой валюте, false, если нет
        //            			"currency" - список всех доступных валют:
        //            				"title" - название валюты
        //            				"name()" - код валюты
        //            			"users" - список всех пользователей:
        //            				"login" - логин пользователя
        //            				"name" - фамилия и имя пользователя
        //            			"passwordErrors" - список ошибок при смене пароля (null, если не выполнялась смена пароля)
        //            			"userAccountsErrors" - список ошибок при редактировании настроек аккаунта (null, если не выполнялось редактирование)
        //            			"cashErrors" - список ошибок при внесении/снятии денег (null, если не выполнялось внесение/снятие)
        //            			"transferErrors" - список ошибок при переводе между своими счетами (null, если не выполнялся перевод)
        //            			"transferOtherErrors" - список ошибок при переводе на счет другого пользователя (null, если не выполнялся перевод)
        return "main.html";
    }
}
