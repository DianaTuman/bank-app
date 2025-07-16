package com.dianatuman.practicum.exchange.dto;

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
}
