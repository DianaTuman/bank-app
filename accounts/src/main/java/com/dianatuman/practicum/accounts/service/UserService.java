package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.dto.UserPasswordDTO;
import com.dianatuman.practicum.accounts.dto.UsersListDTO;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.mapper.AccountMapper;
import com.dianatuman.practicum.accounts.mapper.UserMapper;
import com.dianatuman.practicum.accounts.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
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
        log.info("Get all users from DB");
        return new UsersListDTO(userRepository.findAll().stream().map(userMapper::toDTO).toList());
    }

    public UserPasswordDTO getUserPassword(String login) {
        log.info("Get user {} password from DB", login);
        Optional<User> byId = userRepository.findById(login);
        return byId.map(userMapper::toUserPasswordDTO).orElse(new UserPasswordDTO());
    }

    public void createUser(User user) {
        log.info("Create user {}", user.toString());
        userRepository.save(user);
    }

    public void deleteUser(String login) {
        Optional<User> byId = userRepository.findById(login);
        if (byId.isPresent()) {
            userRepository.delete(byId.get());
            log.info("User {} was deleted", login);
        } else {
            log.warn("User {} doesn't exist and can't be deleted", login);
        }
    }

    public String editUser(UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(userDTO.getLogin());
        if (byId.isPresent()) {
            var user = byId.get();
            if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
                user.setName(userDTO.getName());
                userRepository.save(user);
                log.info("{} user's name was updated", userDTO.getLogin());
            }
            if (userDTO.getBirthdate() != null) {
                user.setBirthdate(userDTO.getBirthdate());
                userRepository.save(user);
                log.info("{} user's birthdate was updated", userDTO.getLogin());
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
            log.info("{} user info was updated", userDTO.getLogin());
            return "OK";
        } else {
            log.error("User {} doesn't exist", userDTO);
            return "User not found";
        }
    }

    public void editPassword(UserPasswordDTO userPasswordDTO) {
        Optional<User> byId = userRepository.findById(userPasswordDTO.getLogin());
        if (byId.isPresent()) {
            User user = byId.get();
            user.setPassword(userPasswordDTO.getPassword());
            userRepository.save(user);
            log.info("Password was changed for user {}", userPasswordDTO.getLogin());
        } else {
            log.error("User {} was not found", userPasswordDTO.getLogin());
        }
    }

    public boolean userExists(String login) {
        log.info("Checking if user {} exists", login);
        return userRepository.findById(login).isPresent();
    }

    public UserDTO getUser(String login) {
        log.info("Get user {} info", login);
        return userRepository.findById(login).map(userMapper::toDTO).orElseThrow();
    }
}
