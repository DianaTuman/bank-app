package com.dianatuman.practicum.accounts.mapper;

import com.dianatuman.practicum.accounts.dto.AccountDTO;
import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.entity.AccountId;
import com.dianatuman.practicum.accounts.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountId toAccountId(AccountDTO accountDTO);

    Account toAccount(AccountDTO accountDTO);

    AccountDTO toDTO(Account account);

    default User mapFromLogin(String login) {
        User user = new User();
        user.setLogin(login);
        return user;
    }

    default String mapFromUser(User user) {
        return user.getLogin();
    }
}
