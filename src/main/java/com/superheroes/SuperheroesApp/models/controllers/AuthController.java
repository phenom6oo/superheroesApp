package com.superheroes.SuperheroesApp.models.controllers;

import com.superheroes.SuperheroesApp.models.models.AuthRequest;
import com.superheroes.SuperheroesApp.models.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public String login(@RequestBody AuthRequest authRequest) {
        System.out.println("Login endpoint invoked");
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        return authService.authenticateAndGenerateToken(username, password);
    }
}
