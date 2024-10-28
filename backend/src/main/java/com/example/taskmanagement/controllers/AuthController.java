package com.example.taskmanagement.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.TokenDTO;
import com.example.taskmanagement.dto.UserDTO;
import com.example.taskmanagement.dto.request.auth.ChangePasswordRequest;
import com.example.taskmanagement.dto.request.auth.LoginRequest;
import com.example.taskmanagement.dto.request.auth.RegisterRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.services.UserService;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            final ModelMapper modelMapper,
            final UserService userService,
            final PasswordEncoder passwordEncoder,
            final JwtUtil jwtUtil) {

        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {

            final User existedUser = userService.findUserByEmail(request.getEmail());
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
                            modelMapper.map(userService.save(user), UserDTO.class)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            final User user = userService.findUserByEmail(request.getEmail());
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
                    user.getUserId().toString(),
                    accessToken);

            return ResponseEntity.ok().body(new ApiResponse(
                    "User Login",
                    tokenDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            // Check If User Exist
            User user = userService.findUserById(request.getUserId());

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            // Check current Password

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Wrong Password", null));
            }

            // Update User with new Password

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            userService.save(user);

            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Password Changed", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
