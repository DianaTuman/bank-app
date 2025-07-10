package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.dto.UserPasswordDTO;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.mapper.UserMapper;
import com.dianatuman.practicum.accounts.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
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

    public void editUser(UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(userDTO.getLogin());
        byId.ifPresent(user -> {
            if (userDTO.getName() != null) {
                user.setName(userDTO.getName());
            }
            if (userDTO.getBirthdate() != null) {
                user.setBirthdate(userDTO.getBirthdate());
            }
            if (userDTO.getEmail() != null) {
                user.setEmail(userDTO.getEmail());
            }
        });
    }

    public void editPassword(UserPasswordDTO userPasswordDTO) {
        Optional<User> byId = userRepository.findById(userPasswordDTO.getLogin());
        byId.ifPresent(user -> {
            user.setPassword(userPasswordDTO.getPassword());
            userRepository.save(user);
        });
    }
}
