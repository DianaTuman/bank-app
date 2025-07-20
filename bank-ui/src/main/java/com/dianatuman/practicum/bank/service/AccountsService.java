package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RefreshScope
@Service
public class AccountsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    @Value("${bank-services.gateway-api}")
    private String gatewayURL;
    HttpHeaders httpHeaders = new HttpHeaders();
    ObjectMapper mapper = new ObjectMapper();

    public AccountsService(RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public UserPasswordDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPasswordDTO userPasswordDTO = restTemplate
                .getForObject(gatewayURL + "/accounts/users/" + username, UserPasswordDTO.class);
        String username1 = userPasswordDTO.getUsername();
        if (username1 == null || username1.isEmpty()) {
            throw new UsernameNotFoundException("User with login " + username + " wasn't found.");
        } else {
            return userPasswordDTO;
        }
    }

    public List<UserDTO> getAllUsers() {
        return restTemplate.getForObject(gatewayURL + "/accounts/users", UsersListDTO.class).getUserDTOS();
    }

    public String registerUser(String login, String password, String name, LocalDate birthdate)
            throws JsonProcessingException {
        String result = "";
        var jsonUserDTO = mapper.writeValueAsString(
                new RegisterUserDTO(login, name, passwordEncoder.encode(password),
                        birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        try {
            restTemplate.postForEntity(gatewayURL + "/accounts/users",
                    new HttpEntity<>(jsonUserDTO, httpHeaders), String.class);
        } catch (HttpServerErrorException errorException) {
            result = "User with this login already exists!";
        } catch (Throwable e) {
            log.error(e.getMessage());
            result = "Internal problems with the user service. Please try later.";
        }
        return result;
    }

    public UserDTO getUserByLogin(String login) {
        return restTemplate.getForObject(gatewayURL + "/accounts/users/" + login + "/info", UserDTO.class);
    }

    public String editPassword(String login, String password) throws JsonProcessingException {
        String result = "";
        var jsonUserDTO = mapper.writeValueAsString(new UserPasswordDTO(login, passwordEncoder.encode(password)));
        try {
            restTemplate.put(gatewayURL + "/accounts/users/" + login + "/password",
                    new HttpEntity<>(jsonUserDTO, httpHeaders));
        } catch (Throwable e) {
            log.error(e.getMessage());
            result = "Internal problems with the user service. Please try later.";
        }
        return result;
    }

    public String editUserAccounts(String login, String name, LocalDate birthdate, String[] accounts) throws JsonProcessingException {
        Long epochSecond = null;
        if (birthdate != null) {
            epochSecond = birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        List<AccountDTO> list = List.of();
        if (accounts != null) {
            list = Arrays.stream(accounts).map(account -> new AccountDTO(login, account)).toList();
        }
        var jsonUserDTO = mapper.writeValueAsString(
                new UserDTO(login, name, epochSecond, list));
        try {
            return restTemplate.postForObject(gatewayURL + "/accounts/users/" + login,
                    new HttpEntity<>(jsonUserDTO, httpHeaders), String.class);
        } catch (Throwable e) {
            return "Internal problems with the user service. Please try later.";
        }
    }
}
