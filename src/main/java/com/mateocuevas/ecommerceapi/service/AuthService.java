package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.dto.LoginDto;
import com.mateocuevas.ecommerceapi.dto.SignUpDto;
import com.mateocuevas.ecommerceapi.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    UserDto login(LoginDto loginDto);
    UserDto signUp(SignUpDto signUpDto);
}
