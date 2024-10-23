package com.example.taskmanagement.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.TokenDTO;
import com.example.taskmanagement.dto.UserDTO;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.request.auth.LoginRequest;
import com.example.taskmanagement.request.auth.RegisterRequest;
import com.example.taskmanagement.response.ApiResponse;
import com.example.taskmanagement.services.AuthService;
import com.example.taskmanagement.utils.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth")
@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            final ModelMapper modelMapper,
            final AuthService authService,
            final PasswordEncoder passwordEncoder,
            final JwtUtil jwtUtil) {

        this.authService = authService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {

            final User existedUser = authService.findUserByEmail(request.getEmail());
            if (existedUser != null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Email Existed", null));
            }
            final User user = User.builder()
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .role(request.getRole())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .createdAt(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            "User Created",
                            modelMapper.map(authService.save(user), UserDTO.class)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            final User user = authService.findUserByEmail(request.getEmail());
            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Wrong Password", null));
            }
            final String accessToken = jwtUtil.generateToken(user);
            final TokenDTO tokenDto = new TokenDTO(
                    accessToken,
                    "refresh_token");

            return ResponseEntity.ok().body(new ApiResponse(
                    "User Login",
                    tokenDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
