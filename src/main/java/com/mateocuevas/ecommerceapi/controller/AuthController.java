package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.LoginDto;
import com.mateocuevas.ecommerceapi.dto.SignUpDto;
import com.mateocuevas.ecommerceapi.dto.UserDto;
import com.mateocuevas.ecommerceapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto){
        UserDto user;
        return ResponseEntity.ok(new user)
    }
    @PostMapping(value = "register")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto user;
        AuthService
        return ResponseEntity.ok(user);
    }
}
