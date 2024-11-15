package com.example.taskmanagement.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.request.auth.UpdateProfileRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.dto.response.UserDTO;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Gender;
import com.example.taskmanagement.enums.Role;
import com.example.taskmanagement.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(final UserService userService, final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> findUserById(@PathVariable String userId) {
        try {
            final User user = userService.findUserById(userId);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "User Found",
                            modelMapper.map(user, UserDTO.class)));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String userId,
            @RequestBody UpdateProfileRequest request) {
        try {
            final User user = userService.findUserById(userId);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            final User updatedUser = userService.updateExistingUser(user, request);

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "User Updated",
                            modelMapper.map(updatedUser, UserDTO.class)));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAllUser() {
        try {
            final List<User> users = userService.findAllUser();

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Users found",
                            users.stream().map(
                                    user -> modelMapper.map(user, UserDTO.class))));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> findByRole(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Gender gender) {
        try {
            final List<User> users = userService.findUserByRoleAndGender(role, gender);

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Users Found",
                            users.stream().map(item -> modelMapper.map(item, UserDTO.class))));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
