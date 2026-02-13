package com.example.VoiceBanking.dto;

import java.math.BigDecimal;

public class AccountDetailsResponse {

    private Long accountId;
    private BigDecimal balance;
    private String fullName;
    private String username;

    public AccountDetailsResponse(Long accountId,
                                  BigDecimal balance,
                                  String fullName,
                                  String username) {
        this.accountId = accountId;
        this.balance = balance;
        this.fullName = fullName;
        this.username = username;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }
}
