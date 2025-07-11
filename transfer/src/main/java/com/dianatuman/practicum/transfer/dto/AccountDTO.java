package com.dianatuman.practicum.transfer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String userLogin;

    private String accountCurrency;

    private Float value;
}
