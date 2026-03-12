package com.ewaste.dto;

public class AuthResponse {

    private String message;
    private String token;
    private String role;

    // --- No-Args Constructor ---
    public AuthResponse() {
    }

    // --- All-Args Constructor ---
    public AuthResponse(String message, String token, String role) {
        this.message = message;
        this.token = token;
        this.role = role;
    }

    // --- Getters and Setters ---

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}