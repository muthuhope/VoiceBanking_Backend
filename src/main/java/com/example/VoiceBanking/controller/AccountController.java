package com.example.VoiceBanking.controller;

import com.example.VoiceBanking.dto.SendMoneyRequest;
import com.example.VoiceBanking.model.Transaction;
import com.example.VoiceBanking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        return ResponseEntity.ok(accountService.getBalance());
    }

    @GetMapping("/details")
    public ResponseEntity<?> getAccountDetails() {
        return ResponseEntity.ok(accountService.getAccountDetails());
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMoney(@RequestBody SendMoneyRequest request) {
        System.out.println("Controller hit");

        accountService.sendMoney(request);
        return ResponseEntity.ok("Transfer successful");
    }
    @GetMapping("/history")
    public List<Transaction> history() {
        return accountService.getTransactionHistory();
    }


}
