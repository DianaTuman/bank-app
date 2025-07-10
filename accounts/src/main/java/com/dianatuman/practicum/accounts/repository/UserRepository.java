package com.dianatuman.practicum.accounts.repository;

import com.dianatuman.practicum.accounts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
