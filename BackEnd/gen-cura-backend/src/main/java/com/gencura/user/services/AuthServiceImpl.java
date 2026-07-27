package com.gencura.user.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gencura.common.exceptions.DuplicateResourceException;
import com.gencura.common.exceptions.InvalidCredentialException;
import com.gencura.common.exceptions.InvalidOperationException;
import com.gencura.common.exceptions.ResourceNotFoundException;
import com.gencura.common.utils.ApiResponse;
import com.gencura.security.CustomUserDetails;
import com.gencura.security.JwtService;
import com.gencura.user.dtos.ChangePasswordRequest;
import com.gencura.user.dtos.LoginResponse;
import com.gencura.user.dtos.LoginUserRequest;
import com.gencura.user.dtos.RegisterUserRequest;
import com.gencura.user.dtos.UserResponse;
import com.gencura.user.entities.User;
import com.gencura.user.entities.UserRole;
import com.gencura.user.repositories.UserRepository;
import com.gencura.user.repositories.UserRoleRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepo;
	private final UserRoleRepository userRoleRepo;
	private final PasswordEncoder passEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	
	@Override
	public ApiResponse<UserResponse> registerUser(RegisterUserRequest req) {
		
		if(userRepo.existsByEmail(req.getEmail()))
			throw new DuplicateResourceException("Email ID already exists "+req.getEmail());
			
		String roleName = normalizeRole(req.getRole());
		
		UserRole usrRole = userRoleRepo.findByRoleAndActiveTrue(roleName).orElseThrow(
				()-> new InvalidCredentialException( "Role not found : " + roleName)
			);
		
		User usr = new User(
				req.getFullName().trim(),
				req.getEmail().trim(),
				req.getMobile().trim(),
				passEncoder.encode(req.getPassword()),
				usrRole
			);
		User saved = userRepo.save(usr);

		return ApiResponse.create("Registration Successfull", toUserResponse(saved));
	}

	@Override
	public ApiResponse<LoginResponse> loginUser(LoginUserRequest req) {
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );
		
		CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
		
		String token = jwtService.generateToken(
                userDetails.getUserId(),
                userDetails.getEmail(),
                userDetails.getRole()
        );
		
		LoginResponse response = new LoginResponse(
				token,
				userDetails.getEmail(),
				userDetails.getUserId(), 
				userDetails.getFullName(), 
				userDetails.getRole()
			);

        return ApiResponse.success( "Login successful.", response);
	}
	 
	@Override
	public ApiResponse<Void> changePassword(ChangePasswordRequest req) {
		User user = userRepo.findByEmailAndActiveTrue(req.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Active user not found."));

        if (!passEncoder.matches(
                req.getOldPassword(),
                user.getPassword())) {

            throw new InvalidOperationException("Old password is incorrect.");
        }

        if (req.getOldPassword().equals(req.getNewPassword())) {

            throw new InvalidOperationException("New password cannot be same as old password.");
        }

        user.setPassword(
                passEncoder.encode(req.getNewPassword()));

        userRepo.save(user);

        return ApiResponse.success("Password changed successfully.",null);
	}
	
	@Override
	public ApiResponse<String> forgotPassword(@Email String email) {

		userRepo.findByEmailAndActiveTrue(email)
        		.orElseThrow(() ->new ResourceNotFoundException("Active user not found."));

		String otp = "120050";
				
				/*
				String.format(
		        "%06d",
		        RANDOM.nextInt(1_000_000));
		        */
		
		/*
		 * TODO
		 * Save OTP
		 * Send OTP using EmailService
		 */
		
		return ApiResponse.success(
		        "OTP sent successfully.",
		        otp);
	}
	
	@Override
	public ApiResponse<Void> resetPassword(@Email String email, @Size(min = 8) String pass) {

		User user = userRepo.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResourceNotFoundException("Active user not found."));

        user.setPassword(
                passEncoder.encode(pass));

        userRepo.save(user);

        return ApiResponse.success("Password reset successfully.",null);
	}
	
	private UserResponse toUserResponse(User user) {

        return new UserResponse(
            user.getId(),
            user.getFullName(),
            user.getRole().getRole(),
            user.isActive(),
            user.getEmail()
        );
    }

    private String normalizeRole(String role) {

        role = role.trim().toUpperCase();

        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        return role;
    }
}
