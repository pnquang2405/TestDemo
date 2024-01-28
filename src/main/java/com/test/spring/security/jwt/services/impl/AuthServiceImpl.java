package com.test.spring.security.jwt.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.spring.security.jwt.models.ERole;
import com.test.spring.security.jwt.models.RefreshToken;
import com.test.spring.security.jwt.models.Role;
import com.test.spring.security.jwt.models.User;
import com.test.spring.security.jwt.payload.request.LoginRequest;
import com.test.spring.security.jwt.payload.request.SignupRequest;
import com.test.spring.security.jwt.payload.request.TokenRefreshRequest;
import com.test.spring.security.jwt.payload.response.JwtResponse;
import com.test.spring.security.jwt.payload.response.MessageResponse;
import com.test.spring.security.jwt.payload.response.TokenRefreshResponse;
import com.test.spring.security.jwt.repository.RoleRepository;
import com.test.spring.security.jwt.repository.UserRepository;
import com.test.spring.security.jwt.security.jwt.JwtUtils;
import com.test.spring.security.jwt.security.services.RefreshTokenService;
import com.test.spring.security.jwt.security.services.UserDetailsImpl;
import com.test.spring.security.jwt.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        JwtUtils jwtUtils;

        @Autowired
        RefreshTokenService refreshTokenService;

        @Autowired
        UserRepository userRepository;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        RoleRepository roleRepository;

        @Override
        public ResponseEntity<MessageResponse> signin(LoginRequest loginRequest) {
                Authentication authentication = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(
                                                loginRequest.getUsername(), loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                String jwt = jwtUtils.generateJwtToken(userDetails);

                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                                .collect(Collectors.toList());

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

                JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                                userDetails.getUsername(), userDetails.getEmail(), roles);

                MessageResponse messageResponse = new MessageResponse();

                messageResponse.setData(jwtResponse);
                messageResponse.setMessage("login successfully");
                messageResponse.setStatus(HttpStatus.OK.value());

                return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
        }

        @Override
        public ResponseEntity<MessageResponse> signup(SignupRequest signUpRequest) {
                MessageResponse messageResponse = new MessageResponse();
                if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                        messageResponse.setMessage("Username is already taken!");
                        messageResponse.setStatus(HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.badRequest().body(messageResponse);
                }

                if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                        messageResponse.setMessage("Email is already in use!");
                        messageResponse.setStatus(HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.badRequest().body(messageResponse);
                }

                // Create new user's account
                User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                                encoder.encode(signUpRequest.getPassword()));
                Set<String> strRoles = signUpRequest.getRole();
                Set<Role> roles = new HashSet<>();

                if (strRoles == null) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Error: Role is not found."));
                        roles.add(userRole);
                } else {
                        strRoles.forEach(role -> {
                                switch (role) {
                                        case "admin":
                                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role is not found."));
                                                roles.add(adminRole);
                                                break;
                                        default:
                                                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role is not found."));
                                                roles.add(userRole);
                                }
                        });
                }

                user.setRoles(roles);
                userRepository.save(user);
                messageResponse.setMessage("User registered successfully.");
                messageResponse.setStatus(HttpStatus.OK.value());

                return ResponseEntity.ok().body(messageResponse);
        }

        @Override
        public ResponseEntity<MessageResponse> refreshtoken(TokenRefreshRequest refreshRequest) {
                String requestRefreshToken = refreshRequest.getRefreshToken();
                MessageResponse messageResponse = new MessageResponse();

                Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(requestRefreshToken);

                if (refreshToken.isPresent()) {
                        refreshTokenService.verifyExpiration(refreshToken.get());
                        User user = refreshToken.get().getUser();
                        String token = jwtUtils.generateTokenFromUsername(user.getUsername());

                        messageResponse.setData(new TokenRefreshResponse(token, requestRefreshToken));
                        messageResponse.setMessage("refresh token is successfully");
                        return ResponseEntity.ok().body(messageResponse);
                } else {
                        messageResponse.setMessage("Token is invalid");
                        messageResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                        return ResponseEntity.badRequest().body(messageResponse);
                }
        }

        @Override
        public ResponseEntity<MessageResponse> logOut() {
                try {
                        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                                        .getAuthentication()
                                        .getPrincipal();
                        Long userId = userDetails.getId();
                        refreshTokenService.deleteByUserId(userId);

                        MessageResponse messageResponse = new MessageResponse("Log out successful!", HttpStatus.OK);

                        return ResponseEntity.ok(messageResponse);
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(new MessageResponse("Internal Server Error",
                                                        HttpStatus.INTERNAL_SERVER_ERROR));
                }
        }
}
