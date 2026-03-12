package com.ewaste.service;

import com.ewaste.dto.LoginRequest;
import com.ewaste.dto.RegisterRequest;
import com.ewaste.dto.LoginResponse;
import com.ewaste.entity.User;
import com.ewaste.enums.Role;
import com.ewaste.enums.Status;
import com.ewaste.repository.UserRepository;
import com.ewaste.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    // Constructor Injection
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== REGISTER =====
    public String register(RegisterRequest request) {

        // check email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        // check username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists";
        }

        User user = new User();

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());

        // encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // default role and status
        user.setRole(Role.USER);
        user.setStatus(Status.PENDING);

        userRepository.save(user);

        return "Registration successful. Waiting for admin approval.";
    }

    // ===== LOGIN (UPDATED) =====
    public LoginResponse login(LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            // Return error response
            return new LoginResponse(null, "Account not found. Please register.", null);
        }

        User user = optionalUser.get();

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(null, "Incorrect password", null);
        }

        // check approval status
        if (user.getStatus() == Status.PENDING) {
            return new LoginResponse(null, "Your account is waiting for admin approval.", null);
        }

        if (user.getStatus() == Status.REJECTED) {
            return new LoginResponse(null, "Your account was rejected by admin.", null);
        }

        // Generate token and return complete response
        String token = jwtService.generateToken(user.getEmail());
        System.err.println(token);

        return new LoginResponse(token, "Login successful", user);
    }
}