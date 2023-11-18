package com.superheroes.SuperheroesApp.models.services;

import com.superheroes.SuperheroesApp.models.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authenticateAndGenerateToken(String username, String password) {
        return jwtTokenProvider.generateToken(username);
    }
}
