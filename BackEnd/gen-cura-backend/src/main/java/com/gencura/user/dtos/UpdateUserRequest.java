package com.gencura.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
	
	@Size(max = 100, min = 2)
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain letters and spaces")
	private String fullName;
	@Email(message = "Email must be valid")
	private String email;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String mobile;
}
