package com.dianatuman.practicum.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDTO {

    private String login;

    private String name;

    private String email;

    private Date birthdate;
}
