package io.sultanov.recipes.api.services;

import io.sultanov.recipes.api.repos.UserRepository;
import io.sultanov.recipes.exceptions.ObjectNotFoundException;
import io.sultanov.recipes.models.RegisterRequest;
import io.sultanov.recipes.models.User;
import io.sultanov.recipes.security.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(ObjectNotFoundException::new);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public Long createUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(Role.USER.getAuthority());
        if (getAllUsers().size() == 0) {
            user.setUserRole(Role.ADMIN.getAuthority());
        }
        return userRepository.save(user).getId();
    }
}
