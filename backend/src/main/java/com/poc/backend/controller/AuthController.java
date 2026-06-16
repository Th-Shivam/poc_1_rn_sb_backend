package com.poc.backend.controller;

import com.poc.backend.dto.SignupRequest;
import com.poc.backend.dto.SignupResponse;
import com.poc.backend.dto.LoginRequest;
import com.poc.backend.dto.LoginResponse;
import com.poc.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles all authentication-related HTTP requests.
 * Endpoints: /api/auth/signup, /api/auth/login
 * 
 * Future Enhancement: Add additional auth endpoints like:
 * - POST /api/auth/refresh-token
 * - POST /api/auth/logout
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor Injection
     * @param authService Service layer for authentication business logic
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint: POST /api/auth/signup
     * Registers a new user with email and password.
     * Password is hashed using BCrypt before storage.
     * 
     * @param request SignupRequest with name, email, password
     * @return SignupResponse with success status and message
     */
    @PostMapping("/signup")
    public SignupResponse signup(
            @RequestBody SignupRequest request
    ) {
        return authService.signup(request);
    }

    /**
     * Endpoint: POST /api/auth/login
     * Authenticates user credentials and returns user details if successful.
     * 
     * Request Body: { "email": "user@example.com", "password": "password123" }
     * 
     * Success Response (HTTP 200):
     * {
     *   "message": "Login successful",
     *   "success": true,
     *   "userId": 1,
     *   "name": "John Doe",
     *   "email": "user@example.com"
     * }
     * 
     * Error Response (HTTP 200):
     * {
     *   "message": "Invalid credentials. Please check your password.",
     *   "success": false,
     *   "userId": null,
     *   "name": null,
     *   "email": null
     * }
     * 
     * Design Note: Returns user details (userId, name, email) which are required
     * for JWT token generation in future implementation.
     * 
     * @param request LoginRequest with email and password
     * @return LoginResponse with authentication result and user details
     */
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}
