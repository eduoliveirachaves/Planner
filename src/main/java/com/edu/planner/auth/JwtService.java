package com.edu.planner.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key to sign and verify JWT tokens from the application.properties file
    public static Key SECRET_KEY;

    // BCryptPasswordEncoder to hash and verify passwords
    private final BCryptPasswordEncoder passwordEncoder;


    public JwtService(@Value("${JWT_SECRET}") String secret, BCryptPasswordEncoder passwordEncoder) {
        SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.passwordEncoder = passwordEncoder;
    }


    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    // Extract the claim from the token - used to extract the user ID
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token - Claim is a key-value pair of the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        return extractUserId(token) != null;
    }


    public String createToken(Long userId) {
        return Jwts.builder()
                .setSubject(Long.toString(userId)) // Set the user ID as the subject - used to extract the user ID from the token
                .setIssuedAt(new Date()) // Current date
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Sign the token with the secret key
                .compact();
    }

    // Hash the password using BCryptPasswordEncoder
    public String getHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Verify the password using BCryptPasswordEncoder
    public boolean matches(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
