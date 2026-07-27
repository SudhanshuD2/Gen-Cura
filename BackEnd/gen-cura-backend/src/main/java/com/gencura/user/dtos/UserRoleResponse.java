package com.gencura.user.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {
	
	private Long id;
	
	private String role;
	
	private LocalDateTime createdAt;
	
	private boolean active;
	
}
