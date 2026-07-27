package com.gencura.user.services;

import java.util.List;

import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.dtos.UserRoleResponse;

public interface UserService {
	ApiResponse<List<UserRoleResponse>> getAllRoles();
	
	ApiResponse<List<UserResponse>> getAllUsersByRole(String role);
	
	ApiResponse<UserResponse> getUserByEmail(String email);
	
	ApiResponse<List<UserResponse>> getAllUsers();
	
	ApiResponse<UserResponse> getUserById(Long id);
	
	ApiResponse<Void> deleteUserById(Long id);
}
