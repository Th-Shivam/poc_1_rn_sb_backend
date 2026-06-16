package com.poc.backend.dto;

public class LoginResponse {

    private String message;
    private boolean success;
    private Long userId;
    private String name;
    private String email;
    private String token;

    // Default Constructor
    public LoginResponse() {
    }

    // Parameterized Constructor (with all fields except token)
    public LoginResponse(String message, boolean success, Long userId, String name, String email) {
        this.message = message;
        this.success = success;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // Constructor containing token
    public LoginResponse(String message, boolean success, Long userId, String name, String email, String token) {
        this.message = message;
        this.success = success;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.token = token;
    }

    // Overloaded Constructor (for error responses without user details)
    public LoginResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
