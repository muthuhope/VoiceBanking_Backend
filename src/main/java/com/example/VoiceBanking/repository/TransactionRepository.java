package com.example.VoiceBanking.repository;

import com.example.VoiceBanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderUsernameOrReceiverUsername(
            String sender, String receiver);
}

