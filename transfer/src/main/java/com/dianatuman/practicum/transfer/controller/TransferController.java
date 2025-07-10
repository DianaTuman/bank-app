package com.dianatuman.practicum.transfer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transfer")
public class TransferController {
    @PostMapping
    public boolean transferAccount() {
        //check blocker
        //send to accounts
        //send notif
        return false;
    }
}
