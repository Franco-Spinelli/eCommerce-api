package com.mateocuevas.ecommerceapi.auth.service;

import com.mateocuevas.ecommerceapi.auth.dto.LoginRequest;
import com.mateocuevas.ecommerceapi.auth.dto.SignUpRequest;
import com.mateocuevas.ecommerceapi.auth.dto.AuthResponse;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.jwt.JwtService;
import com.mateocuevas.ecommerceapi.respository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRespository userRespository;

    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest loginRequest){
        return null;
    }

    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest){
        User user= User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .role(UserRole.CUSTOMER)
                .build();
        userRespository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
