package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService s) { this.userService = s; }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest r) {
        return userService.register(r);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest r) {
        return userService.login(r);
    }
}