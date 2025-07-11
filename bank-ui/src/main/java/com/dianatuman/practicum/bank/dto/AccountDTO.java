package com.dianatuman.practicum.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String userLogin;

    private String accountCurrency;

    private Float value;

    public AccountDTO(String userLogin, String accountCurrency) {
        this.userLogin = userLogin;
        this.accountCurrency = accountCurrency;
    }
}
