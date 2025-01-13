package com.lufthansa.TinyUrl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

//    private final JwtTokenUtil jwtTokenUtil;
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthenticationController(JwtTokenUtil jwtTokenUtil, UserService userService, PasswordEncoder passwordEncoder) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        var user = userService.findByUsername(loginRequest.getUsername());
//        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            return jwtTokenUtil.generateToken(loginRequest.getUsername());
//        }
//        throw new RuntimeException("Invalid username or password");
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@Valid @RequestBody UserEntity userRegistrationDto) {
//        userService.registerUser(userRegistrationDto);
//        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
//    }
}
