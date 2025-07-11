package com.dianatuman.practicum.blocker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class BlockController {

    @PostMapping("/block")
    public Boolean isBlocked(@RequestBody Float amount) {
        return ((Float) 666.0F).equals(amount);
    }
}
