package com.lufthansa.TinyUrl.controller;

import com.lufthansa.TinyUrl.dto.LoginRequest;
import com.lufthansa.TinyUrl.dto.RegisterRequest;
import com.lufthansa.TinyUrl.security.AuthService;
import com.lufthansa.TinyUrl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String message = userService.registerUser(registerRequest);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);

        if (token.equals("User not found!") || token.equals("Invalid password!")) {
            return ResponseEntity.status(401).body(token); // Unauthorized
        }

        return ResponseEntity.ok(token);
    }
}
