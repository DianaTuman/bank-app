package com.dianatuman.practicum.generator.dto;

import com.dianatuman.practicum.generator.enums.BankCurrency;
import lombok.Data;

@Data
public class CurrencyDTO {

    String title;

    String name;

    Float value;

    public CurrencyDTO(BankCurrency bankCurrency, float value) {
        this.title = bankCurrency.getTitle();
        this.name = bankCurrency.name();
        this.value = value;
    }
}
