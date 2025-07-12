package com.dianatuman.practicum.transfer.controller;

import com.dianatuman.practicum.transfer.dto.TransferDTO;
import com.dianatuman.practicum.transfer.service.TransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public boolean transferAccount(@RequestBody TransferDTO transferDTO) throws JsonProcessingException {
        return transferService.transferAccount(transferDTO);
    }
}
