package com.dianatuman.practicum.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatesDTO {

    List<CurrencyDTO> currencyDTOS;

}
