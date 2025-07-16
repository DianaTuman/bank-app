package com.dianatuman.practicum.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashDTO {

    AccountDTO accountDTO;

    Float cashSum;

    public CashDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }
}
