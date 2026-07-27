package com.gencura.user.services;

import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.ChangePasswordRequest;
import com.gencura.user.dtos.LoginResponse;
import com.gencura.user.dtos.LoginUserRequest;
import com.gencura.user.dtos.RegisterUserRequest;
import com.gencura.user.dtos.UserResponse;

public interface AuthService {
	
		ApiResponse<UserResponse> registerUser(RegisterUserRequest req);
		
		ApiResponse<LoginResponse> loginUser(LoginUserRequest req);
		
		ApiResponse<Void> changePassword(ChangePasswordRequest req);
		
		ApiResponse<String> forgotPassword(String email);
		
		ApiResponse<Void> resetPassword(String email, String newPass);
}
