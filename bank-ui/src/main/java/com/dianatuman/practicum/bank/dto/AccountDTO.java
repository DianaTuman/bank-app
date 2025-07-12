package com.dianatuman.practicum.bank.dto;

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

    public AccountDTO(String userLogin, String accountCurrency) {
        this.userLogin = userLogin;
        this.accountCurrency = accountCurrency;
    }
}
