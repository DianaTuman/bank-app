package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.dto.CurrencyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/api")
public class ApiController {

    @GetMapping("/rates")
    public List<CurrencyDTO> getRates() {
        List<CurrencyDTO> result = new ArrayList<>();
        //		Возвращает JSON со списком курсов валют:
        //            		title - название валюты
        //            		name - код валюты
        //            		value - курс валюты по отношению к рублю (для рубля 1)
        return result;
    }
}
