package com.test.spring.security.jwt.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.spring.security.jwt.payload.request.LoginRequest;
import com.test.spring.security.jwt.payload.request.SignupRequest;
import com.test.spring.security.jwt.payload.request.TokenRefreshRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;
import com.test.spring.security.jwt.repository.RoleRepository;
import com.test.spring.security.jwt.repository.UserRepository;
import com.test.spring.security.jwt.security.jwt.JwtUtils;
import com.test.spring.security.jwt.security.services.RefreshTokenService;
import com.test.spring.security.jwt.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  AuthService authService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public ResponseEntity<MessageResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return authService.signin(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return authService.signup(signUpRequest);
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<MessageResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    return authService.refreshtoken(request);
  }

  @PostMapping("/signout")
  public ResponseEntity<MessageResponse> logoutUser() {
    return authService.logOut();
  }

}
