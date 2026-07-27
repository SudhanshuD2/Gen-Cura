package com.gencura.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
	private String token;
    
	@Email(message = "Email should be valid")
	private String email;
	
	private Long id;
	
	@NotBlank
	private String fullName;
	
	@NotBlank
	private String role;
}
