package io.sultanov.recipes.api.services;

import io.sultanov.recipes.exceptions.ObjectAlreadyExistsException;
import io.sultanov.recipes.exceptions.PasswordException;
import io.sultanov.recipes.models.RegisterRequest;
import io.sultanov.recipes.models.User;
import io.sultanov.recipes.security.models.JwtAuthentication;
import io.sultanov.recipes.security.models.LoginRequest;
import io.sultanov.recipes.security.utils.JwtProvider;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public String login(@NonNull LoginRequest authRequest) throws AuthException {
        final User user = userService.findByEmail(authRequest.getEmail());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return jwtProvider.generateAccessToken(user);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password");
        }
    }

    public void register(@NonNull RegisterRequest request) throws AuthException {
        if (!userService.existsByEmail(request.getEmail())) {
            if (Objects.equals(request.getPassword(), request.getPasswordConfirmation())) {
                userService.createUser(request);
            } else {
                throw new PasswordException();
            }
        } else {
            throw new ObjectAlreadyExistsException();
        }
        login(new LoginRequest(request.getEmail(), request.getPassword()));
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
