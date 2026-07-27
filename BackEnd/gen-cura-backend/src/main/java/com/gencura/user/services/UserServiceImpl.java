package com.gencura.user.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.gencura.common.exceptions.EmptyResultException;
import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.dtos.UserRoleResponse;
import com.gencura.user.entities.UserRole;
import com.gencura.user.repositories.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRoleRepository userRoleRepo;
//	private final AuthenticationManager authenticationManager;
//	private final JwtService jwtService;
	
	@Override
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ApiResponse<List<UserRoleResponse>> getAllRoles() {
		
		return ApiResponse.success("All User Roles in the application", toUserRoleResponce(userRoleRepo.findAllByActiveTrue()));
	}
	
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ApiResponse<List<UserResponse>> getAllUsersByRole(String role){
		List<UserResponse> users = new ArrayList<>();
		if(users.size() == 0)
			throw new EmptyResultException("No user with specified Role found");
		return ApiResponse.success("All Users Registered with : "+role, users);
	}
	// Helper
	private List<UserRoleResponse> toUserRoleResponce(List<UserRole> uRoles) {
		List<UserRoleResponse> result = new ArrayList<>();
		for(UserRole ele : uRoles) {
			result.add(new UserRoleResponse(
				ele.getId(),
				ele.getRole(),
				ele.getCreatedAt(),
				ele.isActive())
			);
		}
		
		return result;
	}
}
