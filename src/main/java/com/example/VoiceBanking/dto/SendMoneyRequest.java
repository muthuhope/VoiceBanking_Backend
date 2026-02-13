package com.example.VoiceBanking.dto;

import java.math.BigDecimal;

public class SendMoneyRequest {

    private String receiverUsername;
    private BigDecimal amount;

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
