package com.dianatuman.practicum.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String login;

    private String name;

    private Long birthdate;

    private List<AccountDTO> accounts;
}
