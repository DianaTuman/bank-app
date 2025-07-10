package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AccountsService implements UserDetailsService {

    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public List<UserDTO> getAllUsers() {
        return null;
    }

    public void registerUser(){

    }
}
