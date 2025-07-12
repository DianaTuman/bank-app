package com.dianatuman.practicum.cash.controller;

import com.dianatuman.practicum.cash.dto.CashDTO;
import com.dianatuman.practicum.cash.service.CashService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cash")
public class CashController {

    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping
    public boolean cashAccount(@RequestBody CashDTO cashDTO) throws JsonProcessingException {
        return cashService.cashAccount(cashDTO);
    }

}
