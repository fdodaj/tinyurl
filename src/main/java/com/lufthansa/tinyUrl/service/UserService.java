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
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return "Username is already taken";
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodedPassword);
        user.setUserRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}