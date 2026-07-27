package com.gencura.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.ChangePasswordRequest;
import com.gencura.user.dtos.LoginResponse;
import com.gencura.user.dtos.LoginUserRequest;
import com.gencura.user.dtos.RegisterUserRequest;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@Validated
public class AuthController {
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	// Register User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody RegisterUserRequest request) {

        ApiResponse<UserResponse> response =
                authService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    
    // Login User
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(
            @Valid @RequestBody LoginUserRequest request) {

        ApiResponse<LoginResponse> response =
                authService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    // Change Password
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        ApiResponse<Void> response =
                authService.changePassword(request);

        return ResponseEntity.ok(response);
    }

    // Forgot Password
    @GetMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @Valid @RequestBody String email) {

        ApiResponse<String> response =
                authService.forgotPassword(email);

        return ResponseEntity.ok(response);
    }

    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody String password, @Valid @RequestParam String email) {

        ApiResponse<Void> response =
                authService.resetPassword(email, password);

        return ResponseEntity.ok(response);
    }
}
