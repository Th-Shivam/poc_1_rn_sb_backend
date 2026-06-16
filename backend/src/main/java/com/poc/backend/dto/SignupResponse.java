package com.poc.backend.dto;

public class SignupResponse {

    private String message;
    private boolean success;

    // 1. Default Constructor
    public SignupResponse() {
    }

    // 2. Parameterized Constructor
    public SignupResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // 3. Getters and Setters
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
}