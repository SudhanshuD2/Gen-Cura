package com.gencura.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
	private Long id;
	private String fullName;
	private String role;
	private Boolean active;
	private String email;
}
