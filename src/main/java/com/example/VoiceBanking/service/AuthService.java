package com.example.VoiceBanking.service;

import com.example.VoiceBanking.dto.AuthResponse;
import com.example.VoiceBanking.dto.LoginRequest;
import com.example.VoiceBanking.dto.RegisterRequest;
import com.example.VoiceBanking.model.Account;
import com.example.VoiceBanking.model.User;
import com.example.VoiceBanking.repository.AccountRepository;
import com.example.VoiceBanking.repository.UserRepository;
import com.example.VoiceBanking.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        Account account = new Account();
        account.setUser(user);

        BigDecimal balance =
                request.getInitialBalance() != null
                        ? request.getInitialBalance()
                        : BigDecimal.ZERO;

        account.setBalance(balance);

        accountRepository.save(account);
    }


    public ResponseEntity<AuthResponse> login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
