package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.CurrencyDTO;
import com.dianatuman.practicum.bank.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.bank.dto.RatesDTO;
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
