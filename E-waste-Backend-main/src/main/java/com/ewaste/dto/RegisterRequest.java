package com.ewaste.dto;

public class RegisterRequest {

    private String name;
    private String username;
    private String phone;
    private String email;
    private String password;

    // --- No-Args Constructor ---
    public RegisterRequest() {
    }

    // --- All-Args Constructor ---
    public RegisterRequest(String name, String username, String phone, String email, String password) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    // --- Getters and Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}