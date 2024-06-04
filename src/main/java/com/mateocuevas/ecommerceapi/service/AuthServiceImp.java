package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.dto.LoginDto;
import com.mateocuevas.ecommerceapi.dto.SignUpDto;
import com.mateocuevas.ecommerceapi.dto.UserDto;
import com.mateocuevas.ecommerceapi.respository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{
    private final UserRespository userRespository;
    @Override
    public UserDto login(LoginDto loginDto){

    }

    @Override
    public UserDto signUp(SignUpDto signUpDto){



    }
}
