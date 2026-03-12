package com.ewaste.controller;

import com.ewaste.dto.LoginRequest;
import com.ewaste.dto.RegisterRequest;

import com.ewaste.dto.LoginResponse;

import com.ewaste.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ===== REGISTER =====
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        String message = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(message);
    }

    // ===== LOGIN (UPDATED) =====
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        // If token is null, login failed
        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response.getMessage());
        }

        // Login successful - return complete response
        return ResponseEntity.ok(response);
    }

}