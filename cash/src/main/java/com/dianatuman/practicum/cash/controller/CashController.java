package com.dianatuman.practicum.cash.controller;

import com.dianatuman.practicum.cash.dto.CashDTO;
import com.dianatuman.practicum.cash.service.CashService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cash")
public class CashController {

    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping
    public boolean cashAccount(@RequestBody CashDTO cashDTO) {
        return cashService.cashAccount(cashDTO);
    }

}
