package com.dianatuman.practicum.accounts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(AccountId.class)
@Table(name = "accounts")
public class Account {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "login")
    @JsonBackReference
    private User userLogin;

    @Id
    @Column(length = 3, nullable = false)
    private String accountCurrency;

    private Float value;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(userLogin, account.userLogin) && Objects.equals(accountCurrency, account.accountCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLogin, accountCurrency);
    }

    public boolean updateValue(Float cashSum) {
        if (value + cashSum > 0) {
            value += cashSum;
            return true;
        } else {
            return false;
        }
    }
}
