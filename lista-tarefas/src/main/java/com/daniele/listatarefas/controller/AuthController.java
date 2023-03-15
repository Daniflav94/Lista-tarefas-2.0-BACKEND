package com.daniele.listatarefas.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniele.listatarefas.dto.CredenciaisDTO;
import com.daniele.listatarefas.dto.TokenDTO;
import com.daniele.listatarefas.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public TokenDTO login(@Valid @RequestBody CredenciaisDTO dto) {
        return authService.login(dto);
    }

    
    
}
