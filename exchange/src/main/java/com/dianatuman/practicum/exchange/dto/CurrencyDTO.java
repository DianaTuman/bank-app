package com.dianatuman.practicum.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

    String title;

    String name;

    Float value;

}
