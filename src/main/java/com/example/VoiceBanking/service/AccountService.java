package com.example.VoiceBanking.service;

import com.example.VoiceBanking.dto.AccountDetailsResponse;
import com.example.VoiceBanking.dto.SendMoneyRequest;
import com.example.VoiceBanking.exception.GlobalExceptionHandler;
import com.example.VoiceBanking.exception.InsufficientBalanceException;
import com.example.VoiceBanking.model.Account;
import com.example.VoiceBanking.model.Transaction;
import com.example.VoiceBanking.model.User;
import com.example.VoiceBanking.repository.AccountRepository;
import com.example.VoiceBanking.repository.TransactionRepository;
import com.example.VoiceBanking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;



    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    private User getCurrentUser() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        throw new RuntimeException("Unauthorized");
    }

    public BigDecimal getBalance() {
        User user = getCurrentUser();
        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public AccountDetailsResponse getAccountDetails() {

        User user = getCurrentUser();

        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountDetailsResponse(
                account.getId(),
                account.getBalance(),
                user.getFullName(),
                user.getUsername()
        );
    }

    @Transactional
    public void sendMoney(SendMoneyRequest request) {

        User senderUser = getCurrentUser();

        Account senderAccount = accountRepository.findByUser(senderUser)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        User receiverUser = userRepository.findByUsername(request.getReceiverUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account receiverAccount = accountRepository.findByUser(receiverUser)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (senderAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // 💰 Transfer
        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(request.getAmount()));

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        // 🧾 TRANSACTION HISTORY (PUT HERE 👇)
        Transaction transaction = new Transaction();
        transaction.setSenderUsername(senderUser.getUsername());
        transaction.setReceiverUsername(receiverUser.getUsername());
        transaction.setAmount(request.getAmount());
        transaction.setType("SENT");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        System.out.println("Money sent & transaction saved");
    }

    public List<Transaction> getTransactionHistory() {

        User user = getCurrentUser();

        return transactionRepository
                .findBySenderUsernameOrReceiverUsername(
                        user.getUsername(),
                        user.getUsername()
                );
    }

//    public Object getAccountByUsername(String username) {
//        return accountRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("Account not found"));
//    }

}
