package io.sultanov.recipes.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String passwordConfirmation;
    private String firstName;
    private String lastName;
}
