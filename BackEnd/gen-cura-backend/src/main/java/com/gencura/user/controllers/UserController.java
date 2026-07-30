package com.gencura.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.UpdateUserRequest;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.dtos.UserRoleResponse;
import com.gencura.user.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	// Only ADMINs controller
	@GetMapping("/roles")
	@SecurityRequirement(name="Bearer Authentication")
	public ResponseEntity<ApiResponse<List<UserRoleResponse>>> getAllRoles(){
		return ResponseEntity.ok(userService.getAllRoles());
	}
	
	@GetMapping("/role/{role}")
	@SecurityRequirement(name="Bearer Authentication")
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllByRole(@Valid @PathVariable String role){
		return ResponseEntity.ok(userService.getAllUsersByRole(role));
	}
	@GetMapping("/get-all")
	@SecurityRequirement(name="Bearer Authentication")
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/email/{email}")
	@SecurityRequirement(name="Bearer Authentication")
	public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(@PathVariable @Email String email){
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> updateUserById(
			@PathVariable Long id, @Valid @RequestBody UpdateUserRequest req){
		return ResponseEntity.ok(userService.updateUserById(id, req));
	}
	
	@DeleteMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponse<Void>> deactivateUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.deactivateUserById(id));
	}
	
	@PutMapping("/{id}/activate")
	public ResponseEntity<ApiResponse<UserResponse>> activateUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.activateUserById(id));
	}
	
	// Current User controller
	@PutMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@Valid @RequestBody UpdateUserRequest req){
		return ResponseEntity.ok(userService.updateMyProfile(req));
	}
	
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> getProfile(){
		return ResponseEntity.ok(userService.getMyProfile());
	}
}