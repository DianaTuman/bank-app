package com.dianatuman.practicum.blocker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class BlockController {

    @PostMapping("/block")
    public Boolean isBlocked(@RequestBody Float amount) {
        log.info("Blocker checking for suspicious activity");
        return ((Float) 666.0F).equals(amount);
    }
}
