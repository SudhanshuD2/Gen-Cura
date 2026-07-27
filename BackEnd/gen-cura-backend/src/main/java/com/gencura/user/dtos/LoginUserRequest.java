package com.gencura.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
	
	@Pattern(regexp = "^$|^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String mobile;
	
	@Email(message = "Email must be valid")
	private String email;
	
	@NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "Password must be alphanumeric and contain at least 8 characters"
    )
	private String password;
}
