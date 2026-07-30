package com.gencura.user.services;

import java.util.List;

import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.UpdateUserRequest;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.dtos.UserRoleResponse;

public interface UserService {
	// ADMIN accessible APIs
	ApiResponse<List<UserRoleResponse>> getAllRoles();
	
	ApiResponse<List<UserResponse>> getAllUsersByRole(String role);
	
	ApiResponse<List<UserResponse>> getAllUsers();
	
	ApiResponse<UserResponse> getUserByEmail(String email);
	
	ApiResponse<UserResponse> getUserById(Long id);
	
	ApiResponse<UserResponse> updateUserById(Long id, UpdateUserRequest req);
	
	ApiResponse<UserResponse> activateUserById(Long id);
	
	ApiResponse<Void> deactivateUserById(Long id);
	
	// self user accessible APIs 
	ApiResponse<UserResponse> getMyProfile();
	
	ApiResponse<UserResponse> updateMyProfile(UpdateUserRequest req);
}
