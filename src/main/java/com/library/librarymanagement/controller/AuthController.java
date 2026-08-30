package com.library.librarymanagement.controller;

import com.library.librarymanagement.config.JwtUtil;
import com.library.librarymanagement.controller.dto.LoginRequest;
import com.library.librarymanagement.controller.dto.LoginResponse;
import com.library.librarymanagement.controller.dto.RegisterRequest;
import com.library.librarymanagement.controller.dto.RegisterResponse;
import com.library.librarymanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for member registration and user authentication / JWT token generation.")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new library user / member")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return new RegisterResponse("User registered successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate credentials and obtain JWT Bearer Token")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        String token = jwtUtil.generateToken(username, role);

        return new LoginResponse(token);
    }
}
