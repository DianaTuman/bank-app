package com.dianatuman.practicum.exchange.service;

import com.dianatuman.practicum.exchange.dto.CurrencyDTO;
import com.dianatuman.practicum.exchange.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.exchange.dto.RatesDTO;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private final RatesDTO currentRates = new RatesDTO();

    public RatesDTO getRates() {
        return currentRates;
    }

    public void setRates(RatesDTO ratesDTO) {
        currentRates.setCurrencyDTOS(ratesDTO.getCurrencyDTOS());
    }

    public Float exchange(CurrencyTransferDTO currencyTransferDTO) {
        CurrencyDTO fromCurrency = currentRates.getRate(currencyTransferDTO.getFromCurrency())
                .orElseThrow();
        CurrencyDTO toCurrency = currentRates.getRate(currencyTransferDTO.getToCurrency())
                .orElseThrow();
        Float cashSumInRub = currencyTransferDTO.getAmount() / fromCurrency.getValue();
        return cashSumInRub * toCurrency.getValue();
    }
}
