package com.dianatuman.practicum.accounts.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String login;

    private String name;

    private String password;

    private Long birthdate;

    @OneToMany(mappedBy = "userLogin")
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

}
