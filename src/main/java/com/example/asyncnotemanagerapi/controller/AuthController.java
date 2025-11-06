// File: src/main/java/com/example/asyncnotemanagerapi/controller/AuthController.java
package com.example.asyncnotemanagerapi.controller;

import com.example.asyncnotemanagerapi.dto.LoginRequest;
import com.example.asyncnotemanagerapi.dto.RegisterRequest;
import com.example.asyncnotemanagerapi.model.security.Role;
import com.example.asyncnotemanagerapi.model.security.User;
import com.example.asyncnotemanagerapi.repository.RoleRepository;
import com.example.asyncnotemanagerapi.repository.UserRepository;
import com.example.asyncnotemanagerapi.security.JwtUtil;
import com.example.asyncnotemanagerapi.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Role userRole = roleRepository.findByName(AppConstants.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, AppConstants.ROLE_USER)));

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);

        logger.info("New user registered: {}", request.username());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        String token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(token);
    }
}
