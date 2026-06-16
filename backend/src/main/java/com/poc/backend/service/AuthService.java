package com.poc.backend.service;

import com.poc.backend.dto.SignupRequest;
import com.poc.backend.dto.SignupResponse;
import com.poc.backend.dto.LoginRequest;
import com.poc.backend.dto.LoginResponse;
import com.poc.backend.entity.User;
import com.poc.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService handles authentication-related business logic.
 * Current responsibilities: User signup and login
 * 
 * Future Enhancement: This service will be extended to support:
 * - JWT token generation
 * - Token validation
 * - Refresh token logic
 * - User authentication state management
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Constructor Injection for dependencies.
     * Keeps the service loosely coupled and easily testable.
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user with hashed password.
     * 
     * Flow:
     * 1. Check if email already exists
     * 2. Hash the provided password using BCrypt
     * 3. Create and save new User entity
     * 4. Return success response
     * 
     * @param request SignupRequest containing name, email, and password
     * @return SignupResponse with success status and message
     */
    public SignupResponse signup(SignupRequest request) {

        // Step 1: Check if email already exists
        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser != null) {
            return new SignupResponse(
                    "Email already exists",
                    false
            );
        }

        // Step 2: Create new User object
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        
        // SECURITY: Hash password before storing in database
        // BCrypt uses salt and multiple iterations to prevent brute-force attacks
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        // Step 3: Save user to database
        userRepository.save(user);

        // Step 4: Return success response
        return new SignupResponse(
                "User registered successfully",
                true
        );
    }

    /**
     * Authenticates a user and returns their details if credentials are valid.
     * 
     * Flow:
     * 1. Find user by email
     * 2. Return error if user not found
     * 3. Compare entered password with stored BCrypt hash
     * 4. Return error if password doesn't match
     * 5. Return user details if authentication succeeds
     * 
     * Design Note: Returns userId, name, and email to support future JWT implementation.
     * These fields can be used as JWT payload without additional database queries.
     * 
     * @param request LoginRequest containing email and password
     * @return LoginResponse with success status, message, and user details if successful
     */
    public LoginResponse login(LoginRequest request) {

        // Step 1: Find user by email
        User user = userRepository.findByEmail(request.getEmail());

        // Step 2: Return error if user not found
        if (user == null) {
            return new LoginResponse(
                    "User not found. Please check your email or sign up.",
                    false
            );
        }

        // Step 3: Verify password using BCrypt comparison
        // passwordEncoder.matches() safely compares plain text with hashed password
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        // Step 4: Return error if password doesn't match
        if (!passwordMatches) {
            return new LoginResponse(
                    "Invalid credentials. Please check your password.",
                    false
            );
        }

        // Step 5: Generate JWT token
        String token = jwtService.generateToken(user.getEmail(), user.getName(), user.getId());

        // Step 6: Return success response with user details and JWT token
        return new LoginResponse(
                "Login successful",
                true,
                user.getId(),
                user.getName(),
                user.getEmail(),
                token
        );
    }
}