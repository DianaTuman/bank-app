package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.RegisterUserDTO;
import com.dianatuman.practicum.bank.dto.UserDTO;
import com.dianatuman.practicum.bank.dto.UserPasswordDTO;
import com.dianatuman.practicum.bank.dto.UsersListDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@RefreshScope
@Service
public class AccountsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    //    @Value("${bank-services.accounts}")
    private String accountsServiceURL = "http://accounts-service";
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
                .getForObject(accountsServiceURL + "/users/" + username, UserPasswordDTO.class);
        String username1 = userPasswordDTO.getUsername();
        if (username1 == null || username1.isEmpty()) {
            throw new UsernameNotFoundException("User with login " + username + " wasn't found.");
        } else {
            return userPasswordDTO;
        }
    }

    public List<UserDTO> getAllUsers() {
        return restTemplate.getForObject(accountsServiceURL + "/users", UsersListDTO.class).getUserDTOS();
    }

    public String registerUser(String login, String password, String name, LocalDate birthdate) throws JsonProcessingException {
        String result = "";
        var jsonUserDTO = mapper.writeValueAsString(
                new RegisterUserDTO(login, name, passwordEncoder.encode(password), birthdate.toEpochDay()));
        try {
            restTemplate.postForEntity(accountsServiceURL + "/users",
                    new HttpEntity<>(jsonUserDTO, httpHeaders), String.class);
        } catch (HttpServerErrorException errorException) {
            result = "User with this login already exists!";
        } catch (Throwable e) {
            result = "Internal problems with the user service. Please try later.";
        }
        return result;
    }

    public UserDTO getUserByName(String username) {
        return restTemplate.getForObject(accountsServiceURL + "/users/" + username + "/info", UserDTO.class);
    }
}
