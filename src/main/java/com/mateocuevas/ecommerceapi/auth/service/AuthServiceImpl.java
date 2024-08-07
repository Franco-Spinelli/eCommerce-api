package com.mateocuevas.ecommerceapi.auth.service;

import com.mateocuevas.ecommerceapi.auth.dto.LoginRequest;
import com.mateocuevas.ecommerceapi.auth.dto.SignUpRequest;
import com.mateocuevas.ecommerceapi.auth.dto.AuthResponse;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.exception.EmailAlreadyExistsException;
import com.mateocuevas.ecommerceapi.jwt.JwtService;
import com.mateocuevas.ecommerceapi.respository.UserRepository;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CartService cartService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails user = userService.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        String role = jwtService.getRoleFromUser(user);
        return AuthResponse.builder()
                .token(token)
                .role(role)
                .build();
    }

    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        if (userService.findByUsername(signUpRequest.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .firstName(signUpRequest.getFirstName())
                    .lastName(signUpRequest.getLastName())
                    .role(UserRole.ROLE_CUSTOMER)
                    .build();
            userService.save(user);
            if (user.getRole().equals(UserRole.ROLE_CUSTOMER)) {
                cartService.saveCart(Cart.builder()
                        .id(null)
                        .cartItems(null)
                        .customer(user)
                        .totalItems(0)
                        .totalPrice(0)
                        .build()
                );
            }

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .role(jwtService.getRoleFromUser(user))
                    .build();
        } else {
            throw new EmailAlreadyExistsException("The email address is already registered.");
        }
    }

    @Override
    public AuthResponse signUpAdmin(SignUpRequest signUpRequest) {
        if (userService.findByRole(UserRole.ROLE_ADMIN).isEmpty()) {
            if (userService.findByUsername(signUpRequest.getUsername()).isEmpty()) {
                User user = User.builder()
                        .username(signUpRequest.getUsername())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .firstName(signUpRequest.getFirstName())
                        .lastName(signUpRequest.getLastName())
                        .role(UserRole.ROLE_ADMIN)
                        .build();
                userService.save(user);
                return AuthResponse.builder()
                        .token(jwtService.getToken(user))
                        .build();
            } else {
                throw new EmailAlreadyExistsException("The email address is already registered.");
            }
        } else {
            throw new RuntimeException("user admin already created");
        }
    }
}
