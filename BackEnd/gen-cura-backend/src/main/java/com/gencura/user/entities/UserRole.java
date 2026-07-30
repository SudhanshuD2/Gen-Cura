package com.gencura.user.entities;

import java.util.ArrayList;
import java.util.List;

import com.gencura.common.entities.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AttributeOverride(name="id", column = @Column(name="role_id"))
public class UserRole extends BaseEntity{
	
	@Pattern(regexp = "^ROLE_[A-Z_]+$", message = "Input must contain only uppercase letters and underscores")
	private String role;
	
	@OneToMany(mappedBy = "role")
	private List<User> users = new ArrayList<>();
}
