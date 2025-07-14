package com.dianatuman.practicum.notifications.controller;

import com.dianatuman.practicum.notifications.dto.CashDTO;
import com.dianatuman.practicum.notifications.dto.TransferDTO;
import com.dianatuman.practicum.notifications.service.NotificationsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @PostMapping("transfer")
    public void sendTransferNotification(@RequestBody TransferDTO transferDTO) {
        notificationsService.sendNotification(transferDTO.formMessage());
    }

    @PostMapping("cash")
    public void sendCashNotification(@RequestBody CashDTO cashDTO) {
        notificationsService.sendNotification(cashDTO.formMessage());
    }
}
