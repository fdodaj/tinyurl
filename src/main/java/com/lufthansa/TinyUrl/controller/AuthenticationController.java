package com.lufthansa.TinyUrl.controller;

import com.lufthansa.TinyUrl.dto.LoginRequest;
import com.lufthansa.TinyUrl.security.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String token = jwtTokenUtil.generateToken(loginRequest.getUsername());
        return token;
    }
}
