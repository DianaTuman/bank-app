package com.dianatuman.practicum.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyTransferDTO {

    String fromCurrency;

    String toCurrency;

    Float amount;

    public CurrencyTransferDTO(TransferDTO transferDTO) {
        this.fromCurrency = transferDTO.getFromAccountDTO().getAccountCurrency();
        this.toCurrency = transferDTO.getToAccountDTO().getAccountCurrency();
        this.amount = transferDTO.getAmountFrom();
    }
}
