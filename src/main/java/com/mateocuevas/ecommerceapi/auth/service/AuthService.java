package com.mateocuevas.ecommerceapi.auth.service;

import com.mateocuevas.ecommerceapi.auth.dto.LoginRequest;
import com.mateocuevas.ecommerceapi.auth.dto.SignUpRequest;
import com.mateocuevas.ecommerceapi.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse signUp(SignUpRequest signUpRequest);
    AuthResponse signUpAdmin(SignUpRequest signUpRequest);
}
