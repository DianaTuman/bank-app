package com.dianatuman.practicum.generator.enums;

import lombok.Getter;

@Getter
public enum BankCurrency {
    RUB("Russian ruble"),
    USD("United States dollar"),
    CNY("Chinese yuan");

    final String title;

    BankCurrency(String title) {
        this.title = title;
    }
}
