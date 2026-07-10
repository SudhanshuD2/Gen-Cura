package com.gencura.user.entities;

import com.gencura.common.entities.BaseEntity;
import com.gencura.common.enums.UserRole;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
	
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;
	
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@Column(nullable=false, length = 15)
	private String mobile;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
}
