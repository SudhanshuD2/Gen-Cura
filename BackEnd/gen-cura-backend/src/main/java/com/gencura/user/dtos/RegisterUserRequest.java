package com.gencura.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {
	
	@NotBlank(message = "Full name is mandatory to register")
	@Size(max = 100, min = 2)
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain letters and spaces")
	private String fullName;
	
	@NotBlank(message = "Email is mandatory for registration")
	@Email(message = "Email must be valid")
	private String email;
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String mobile;
	
	@NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "[No special chars] Password must be alphanumeric and contain at least 8 characters"
    )
	private String password;
	
	@NotNull(message = "User role is mandatory")
	private String role;
}
