package com.gencura.user.entities;

import com.gencura.common.entities.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter	
@NoArgsConstructor
@AllArgsConstructor
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
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private UserRole role;
	
}
