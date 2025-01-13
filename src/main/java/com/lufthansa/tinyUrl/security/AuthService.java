package com.lufthansa.tinyUrl.security;

import com.lufthansa.tinyUrl.dto.LoginRequest;
import com.lufthansa.tinyUrl.entity.UserEntity;
import com.lufthansa.tinyUrl.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateUser(LoginRequest loginRequest) {
        // Retrieve the user wrapped in Optional
        Optional<UserEntity> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        // If the user is not found, return an error message
        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        // Get the user entity from the Optional
        UserEntity user = userOptional.get();

        // Check if the password matches
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        // Generate and return the JWT token
        return jwtUtil.generateToken(user.getUsername());
    }

}