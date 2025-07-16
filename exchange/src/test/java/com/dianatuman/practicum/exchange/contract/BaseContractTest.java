package com.dianatuman.practicum.exchange.contract;

import com.dianatuman.practicum.exchange.ExchangeApplication;
import com.dianatuman.practicum.exchange.controller.ExchangeController;
import com.dianatuman.practicum.exchange.dto.CurrencyDTO;
import com.dianatuman.practicum.exchange.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.exchange.dto.RatesDTO;
import com.dianatuman.practicum.exchange.service.ExchangeService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;

@SpringBootTest(classes = ExchangeApplication.class)
public class BaseContractTest {

    @Autowired
    ExchangeController exchangeController;

    @MockitoBean
    ExchangeService exchangeService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(exchangeController);
        Mockito.when(exchangeService.getRates())
                .thenReturn(new RatesDTO(Arrays.asList(
                        new CurrencyDTO("Russian Ruble", "RUB", 1.0f),
                        new CurrencyDTO("United States Dollar", "USD", 0.01f)
                )));
        Mockito.when(exchangeService.exchange(new CurrencyTransferDTO("RUB", "USD", 100.0f)))
                .thenReturn(1.0f);
    }
}
