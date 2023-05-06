package io.sultanov.recipes.api.controllers;

import io.sultanov.recipes.api.services.AuthService;
import io.sultanov.recipes.models.RegisterRequest;
import io.sultanov.recipes.security.models.LoginRequest;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) throws AuthException {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) throws AuthException {
        authService.register(request);
    }
}
