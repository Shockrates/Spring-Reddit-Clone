package com.example.springredditclone.controller;

import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {
    
    private final  AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity(OK);
    }
}