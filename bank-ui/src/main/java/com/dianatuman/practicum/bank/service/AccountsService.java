package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.UserDTO;
import com.dianatuman.practicum.bank.dto.UserPasswordDTO;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RefreshScope
@Service
public class AccountsService implements UserDetailsService {

    private final RestTemplate restTemplate;

//    @Value("${bank-services.accounts}")
    private String accountsServiceURL = "http://accounts-service";

    public AccountsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
        return null;
    }

    public void registerUser() {

    }
}
