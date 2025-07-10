package com.dianatuman.practicum.accounts.dto;

import com.dianatuman.practicum.accounts.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String login;

    private String name;

    private String email;

    private Date birthdate;

    private List<Account> accounts = new ArrayList<>();
}
