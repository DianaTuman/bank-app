package com.dianatuman.practicum.transfer.controller;

import com.dianatuman.practicum.transfer.dto.TransferDTO;
import com.dianatuman.practicum.transfer.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public boolean transferAccount(@RequestBody TransferDTO transferDTO) {
        return transferService.transferAccount(transferDTO);
    }
}
