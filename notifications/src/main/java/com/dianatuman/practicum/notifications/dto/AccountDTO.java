package com.dianatuman.practicum.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String userLogin;

    private String accountCurrency;

    private Float value;
}
