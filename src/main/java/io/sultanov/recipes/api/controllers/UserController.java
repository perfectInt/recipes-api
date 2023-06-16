package io.sultanov.recipes.api.controllers;

import io.sultanov.recipes.api.services.UserService;
import io.sultanov.recipes.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("")
    public void updateUser(@RequestBody User user) {
        return kewmdklem
    }
}
