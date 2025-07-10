package com.dianatuman.practicum.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/exchange")
public class ExchangeController {

    @GetMapping("/rub")
    public Integer getRub(){
        return 1;
    }
}
