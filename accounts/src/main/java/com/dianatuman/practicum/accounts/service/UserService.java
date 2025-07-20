package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.dto.UserPasswordDTO;
import com.dianatuman.practicum.accounts.dto.UsersListDTO;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.mapper.AccountMapper;
import com.dianatuman.practicum.accounts.mapper.UserMapper;
import com.dianatuman.practicum.accounts.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, AccountService accountService, AccountMapper accountMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    public UsersListDTO getAllUsers() {
        return new UsersListDTO(userRepository.findAll().stream().map(userMapper::toDTO).toList());
    }

    public UserPasswordDTO getUserPassword(String login) {
        Optional<User> byId = userRepository.findById(login);
        return byId.map(userMapper::toUserPasswordDTO).orElse(new UserPasswordDTO());
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(String login) {
        Optional<User> byId = userRepository.findById(login);
        byId.ifPresent(userRepository::delete);
    }

    public String editUser(UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(userDTO.getLogin());
        if (byId.isPresent()) {
            var user = byId.get();
            if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
                user.setName(userDTO.getName());
                userRepository.save(user);
            }
            if (userDTO.getBirthdate() != null) {
                user.setBirthdate(userDTO.getBirthdate());
                userRepository.save(user);
            }
            if (userDTO.getAccounts() != null) {
                var accounts = user.getAccounts().stream().map(accountMapper::toDTO).toList();
                var toDelete = new ArrayList<>(accounts);
                toDelete.removeAll(userDTO.getAccounts());
                toDelete.forEach(accountService::deleteAccount);
                var toAdd = new ArrayList<>(userDTO.getAccounts());
                toAdd.removeAll(accounts);
                toAdd.forEach(accountService::createAccount);
            }
            return "OK";
        } else {
            return "User not found.";
        }
    }

    public void editPassword(UserPasswordDTO userPasswordDTO) {
        Optional<User> byId = userRepository.findById(userPasswordDTO.getLogin());
        byId.ifPresent(user -> {
            user.setPassword(userPasswordDTO.getPassword());
            userRepository.save(user);
        });
    }

    public boolean userExists(String login) {
        return userRepository.findById(login).isPresent();
    }

    public UserDTO getUser(String login) {
        return userRepository.findById(login).map(userMapper::toDTO).orElseThrow();
    }
}
