package com.example.VoiceBanking.controller;

import com.example.VoiceBanking.dto.LoginRequest;
import com.example.VoiceBanking.dto.RegisterRequest;
import com.example.VoiceBanking.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT is stateless → nothing to invalidate
        return ResponseEntity.ok("Logged out successfully");
    }
}
