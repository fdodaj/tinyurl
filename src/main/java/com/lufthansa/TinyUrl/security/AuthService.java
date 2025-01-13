package com.lufthansa.TinyUrl.security;

import com.lufthansa.TinyUrl.dto.LoginRequest;
import com.lufthansa.TinyUrl.entity.UserEntity;
import com.lufthansa.TinyUrl.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return "User not found!";
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}