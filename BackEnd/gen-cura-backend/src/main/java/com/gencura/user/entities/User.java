package com.gencura.user.entities;

import com.gencura.common.entities.BaseEntity;
import com.gencura.common.enums.UserRole;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AttributeOverride(name="id", column = @Column(name="user_id"))
public class User extends BaseEntity{
	
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	@Email
	private String email;
	
	@Column(nullable=false, length = 12)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
//	status
}
