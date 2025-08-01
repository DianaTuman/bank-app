package com.dianatuman.practicum.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCurrencyDTO {

    private CurrencyDTO currency;

    private Float value;

    private boolean exists;
}
