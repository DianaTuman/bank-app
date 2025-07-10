package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.UserPasswordDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AccountsService implements UserDetailsService {

    @Override
    public UserPasswordDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserPasswordDTO empty throw UsernameNotFoundException
        return null;
    }

    public List<UserPasswordDTO> getAllUsers() {
        return null;
    }

    public void registerUser(){

    }
}
