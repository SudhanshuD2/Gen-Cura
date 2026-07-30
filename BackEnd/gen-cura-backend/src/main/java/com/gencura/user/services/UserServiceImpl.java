package com.gencura.user.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gencura.common.exceptions.DuplicateResourceException;
import com.gencura.common.exceptions.EmptyResultException;
import com.gencura.common.exceptions.InvalidOperationException;
import com.gencura.common.exceptions.ResourceNotFoundException;
import com.gencura.common.utils.ApiResponse;
import com.gencura.user.dtos.UpdateUserRequest;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.dtos.UserRoleResponse;
import com.gencura.user.entities.User;
import com.gencura.user.entities.UserRole;
import com.gencura.user.repositories.UserRepository;
import com.gencura.user.repositories.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepo;
	private final UserRoleRepository userRoleRepo;
	
	@Override
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ApiResponse<List<UserRoleResponse>> getAllRoles() {
		List<UserRoleResponse> rolesResp = new ArrayList<>();
		
		for(UserRole ele : userRoleRepo.findAllByActiveTrue())
			rolesResp.add(toUserRoleResponse(ele));
		
		return ApiResponse.success("All User Roles in the application", 
				rolesResp);
	}
	
	@Override
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ApiResponse<List<UserResponse>> getAllUsersByRole(String role){
		 
		UserRole findRole = userRoleRepo.findByRole(role).orElseThrow(
				()-> new ResourceNotFoundException("Requested role not found : "+role)
			);
		
		List<UserResponse> userResps = new ArrayList<>();
		
		for(User u : findRole.getUsers())
			userResps.add(toUserResponse(u));
		
		if(userResps.size() == 0)
			throw new EmptyResultException("No user with specified Role found");
		return ApiResponse.success("All Users Registered with : "+role, userResps);
	}

	@Override
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ApiResponse<UserResponse> getUserByEmail(String email) {
		User user = userRepo.findByEmailAndActiveTrue(email)
	            .orElseThrow(() ->new ResourceNotFoundException(
	                            "User not found with email: " + email));

	    return ApiResponse.success("User found successfully.",
            toUserResponse(user)
	    );
	}

	@Override
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ApiResponse<List<UserResponse>> getAllUsers() {
		List<UserResponse> responseList = new ArrayList<>();
		
		for(User u : userRepo.findAll())
			responseList.add(toUserResponse(u));
	    
	    return ApiResponse.success("All registered users.",
	            responseList
	    );
	}

	@Override
	public ApiResponse<UserResponse> getUserById(Long id) {
		User user = userRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException(
	            		"User not found with id: " + id));

	    return ApiResponse.success(
            "User found successfully.",
            toUserResponse(user)
	    );
	}

	@Override
	public ApiResponse<Void> deactivateUserById(Long id) {
		User user = userRepo.findByIdAndActiveTrue(id)
	            .orElseThrow(() -> new ResourceNotFoundException(
	            		"User not found with id: " + id));
		
	    if ("ROLE_SUPER_ADMIN".equals(user.getRole().getRole())) {
	        throw new IllegalArgumentException("Super Admin cannot be deleted.");
	    }

	    user.setActive(false);

	    return ApiResponse.delete(
	            "User deleted successfully.",
	            null
	    );
	}
	
	@Override
	public ApiResponse<UserResponse> activateUserById(Long id) {
		User user = userRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Requested User not found"));
		if(user.isActive())
			throw new InvalidOperationException("Requested User is already updated/Activated");
		
		user.setActive(true);
		
		return ApiResponse.success("User Activated successfully", toUserResponse(user));
	}
	
	// for ADMINs
	@Override
	@PreAuthorize("hasAnyRole('SUPER_ADMIN','HOSPITAL_ADMIN')")
	public ApiResponse<UserResponse> updateUserById(Long id,
	        UpdateUserRequest req) {

	    User user = userRepo.findByIdAndActiveTrue(id)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

	    return ApiResponse.success("User updated successfully.",
	            updateUser(user, req));
	}
	
	// for same user -
	@Override
	public ApiResponse<UserResponse> getMyProfile() {
		String email = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		User user = userRepo.findByEmailAndActiveTrue(email)
				.orElseThrow(()-> new ResourceNotFoundException("Some exception has occurred, please try again later"));
		
		return ApiResponse.success("Profile found", toUserResponse(user));
	}
	
	@Override
	public ApiResponse<UserResponse> updateMyProfile(UpdateUserRequest req) {

	    String email = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepo.findByEmailAndActiveTrue(email)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

	    return ApiResponse.success("Profile updated successfully.",
	            updateUser(user, req)
	    );
	}
	
	// private helper methods
	private UserResponse updateUser(User user, UpdateUserRequest req) {

	    if (req.getFullName() != null &&
	            !req.getFullName().isBlank()) {
	        user.setFullName(req.getFullName().trim());
	    }

	    if (req.getEmail() != null &&
	            !req.getEmail().equalsIgnoreCase(user.getEmail())) {

	        if (userRepo.existsByEmail(req.getEmail())) {
	            throw new DuplicateResourceException("Email already exists.");
	        }

	        user.setEmail(req.getEmail().trim());
	    }

	    if (req.getMobile() != null &&
	            !req.getMobile().equals(user.getMobile())) {

	        if (userRepo.existsByMobile(req.getMobile())) {
	            throw new DuplicateResourceException("Mobile already exists.");
	        }

	        user.setMobile(req.getMobile());
	    }

	    return new UserResponse(
	    		user.getId(),
	    		user.getFullName(), 
	    		user.getRole().getRole(), 
	    		user.isActive(), 
	    		user.getEmail()
	    		);
	}
	private UserResponse toUserResponse(User user){
		return new UserResponse(
			user.getId(),
			user.getFullName(),
			user.getRole().getRole(),
			user.isActive(),
			user.getEmail()
		);
	}
	private UserRoleResponse toUserRoleResponse(UserRole uRole) {
		return new UserRoleResponse(
			uRole.getId(),
			uRole.getRole(),
			uRole.getCreatedAt(),
			uRole.isActive()
		);
	}
}
