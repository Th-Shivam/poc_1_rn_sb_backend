package com.poc.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    // Using a fallback secure-length key if none is configured in properties
    @Value("${jwt.secret:defaultSecretKeyForPocProjectWithAtLeast256BitsMinimumBitLengthRequired}")
    private String secretKey;

    @Value("${jwt.expiration-ms:86400000}") // 24 hours default
    private long expirationMs;

    /**
     * Generates a signed JWT token containing email as subject and basic user claims.
     */
    public String generateToken(String email, String name, Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(email)
                .withClaim("name", name)
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .sign(algorithm);
    }

    /**
     * Decodes and validates the token. Returns the subject (email) if valid, null otherwise.
     */
    public String validateTokenAndGetEmail(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (Exception e) {
            // Token verification failed (invalid signature, expired, etc.)
            return null;
        }
    }
}
