package com.example.VoiceBanking.repository;

import com.example.VoiceBanking.model.Account;
import com.example.VoiceBanking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}
