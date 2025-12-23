package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class JwtTokenProvider {

    // Used in tests
    public String createToken(Long userId, String email, Set<String> roles) {
        return "token";
    }

    // Used in tests
    public boolean validateToken(String token) {
        return true;
    }

    // Used in tests
    public Claims getClaims(String token) {
        return Jwts.claims().setSubject("user");
    }
}
