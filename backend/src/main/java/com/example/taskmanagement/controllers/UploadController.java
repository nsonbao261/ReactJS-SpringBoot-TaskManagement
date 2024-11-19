package com.example.taskmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.services.UploadService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Upload")
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(final UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(path = "/", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> uploadImage(@RequestBody MultipartFile uploadedFile) {
        try {

            String downloadUrl = uploadService.upload(uploadedFile);

            return ResponseEntity.ok()
                    .body(new ApiResponse("File Uploaded", downloadUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
