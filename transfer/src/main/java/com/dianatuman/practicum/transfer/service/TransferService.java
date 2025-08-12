package com.dianatuman.practicum.transfer.service;

import com.dianatuman.practicum.transfer.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.transfer.dto.TransferDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class TransferService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${accounts_service_url}")
    private String accountsServiceURL;
    @Value("${blocker_service_url}")
    private String blockerServiceURL;
    @Value("${exchange_service_url}")
    private String exchangeServiceURL;
    @Value("${bank_app_notifications_topic}")
    private String notificationsTopic;


    public TransferService(RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String transferAccount(TransferDTO transferDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();

        boolean isBlocked = Boolean.TRUE.equals(restTemplate
                .postForObject(blockerServiceURL + "/block", Math.abs(transferDTO.getAmountFrom()), Boolean.class));
        if (isBlocked) {
            return "Operation was blocked as suspicious.";
        } else {
            CurrencyTransferDTO currencyTransferDTO = new CurrencyTransferDTO(transferDTO);
            var jsonCurrencyTransferDTO = mapper.writeValueAsString(currencyTransferDTO);
            Float amountTo;
            try {
                log.info(exchangeServiceURL + "/exchange");
                amountTo = restTemplate.postForObject(exchangeServiceURL + "/exchange",
                        new HttpEntity<>(jsonCurrencyTransferDTO, httpHeaders), Float.class);
            } catch (Throwable e) {
                return "Exchange service is not working. Please try later.";
            }
            transferDTO.setAmountTo(amountTo);
            var jsonTransferDTO = mapper.writeValueAsString(transferDTO);
            try {
                log.info(accountsServiceURL + "/accounts/transfer");
                String s = restTemplate.postForObject(accountsServiceURL + "/accounts/transfer",
                        new HttpEntity<>(jsonTransferDTO, httpHeaders), String.class);
                if (Objects.equals(s, "OK")) {
                    kafkaTemplate.send(notificationsTopic, transferDTO.formMessage()).whenComplete((result, e) -> {
                        if (e != null) {
                            log.error("Ошибка при отправке сообщения: {}", e.getMessage(), e);
                            return;
                        }

                        RecordMetadata metadata = result.getRecordMetadata();
                        log.info("Сообщение отправлено. Topic = {}, partition = {}, offset = {}",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    });
                }
                return s;
            } catch (Throwable e) {
                return "Account service is not working. Please try later.";
            }
        }
    }
}
