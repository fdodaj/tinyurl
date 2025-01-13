package com.lufthansa.tinyUrl.utils;

import com.lufthansa.tinyUrl.entity.UserEntity;
import com.lufthansa.tinyUrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    @Autowired
    private UserService userService;


    public UserEntity getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User does not exist in the system"));
    }
}
