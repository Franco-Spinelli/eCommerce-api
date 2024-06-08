package com.mateocuevas.ecommerceapi.auth.service;

import com.mateocuevas.ecommerceapi.auth.dto.LoginRequest;
import com.mateocuevas.ecommerceapi.auth.dto.SignUpRequest;
import com.mateocuevas.ecommerceapi.auth.dto.AuthResponse;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.jwt.JwtService;
import com.mateocuevas.ecommerceapi.respository.UserRespository;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRespository userRespository;
    private final CartService cartService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        UserDetails user=userRespository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token= jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    } 

    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest){
        User user= User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .role(UserRole.CUSTOMER)
                .build();
        userRespository.save(user);
        if(user.getRole().equals(UserRole.CUSTOMER)){
            cartService.saveCart(Cart.builder()
                    .id(null)
                    .cartItems(null)
                    .customer(user)
                    .totalItems((double) 0)
                    .totalPrice((double) 0)
                    .build()
            );
        }

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
