package com.lufthansa.tinyUrl.service;


import com.lufthansa.tinyUrl.dto.RegisterRequest;
import com.lufthansa.tinyUrl.entity.UserEntity;
import com.lufthansa.tinyUrl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(RegisterRequest registerRequest) {
        // Check if the user already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return "Username is already taken";
        }

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Create a new user
        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodedPassword);
        user.setUserRole("USER");  // Default role is USER

        // Save the user to the database
        userRepository.save(user);

        return "User registered successfully";
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}