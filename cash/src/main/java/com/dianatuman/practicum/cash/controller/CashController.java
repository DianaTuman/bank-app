package com.dianatuman.practicum.cash.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cash")
public class CashController {

    @PostMapping
    public boolean cashAccount(){
        //check blocker
        //send to accounts
        //send notif
        return false;
    }

}
