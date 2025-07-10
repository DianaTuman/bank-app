package com.dianatuman.practicum.accounts.mapper;

import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.dto.UserPasswordDTO;
import com.dianatuman.practicum.accounts.entity.User;
import org.mapstruct.Mapping;

public interface UserMapper {

    UserDTO toDTO(User userEntity);

    UserPasswordDTO toUserPasswordDTO(User userEntity);
}
