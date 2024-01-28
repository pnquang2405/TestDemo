package com.test.spring.security.jwt.services;

import org.springframework.http.ResponseEntity;

import com.test.spring.security.jwt.payload.request.LoginRequest;
import com.test.spring.security.jwt.payload.request.SignupRequest;
import com.test.spring.security.jwt.payload.request.TokenRefreshRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;

public interface AuthService {
    ResponseEntity<MessageResponse> signin(LoginRequest loginRequest);

    ResponseEntity<MessageResponse> signup(SignupRequest signupRequest);

    ResponseEntity<MessageResponse> refreshtoken(TokenRefreshRequest refreshRequest);

    ResponseEntity<MessageResponse> logOut();
}
