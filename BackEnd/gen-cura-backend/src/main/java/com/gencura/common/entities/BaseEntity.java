package com.gencura.common.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@Column(name="is_active", nullable = false)
	private boolean isActive = true;
	
	// --- relational
	/*
	@Column(name = "created_by", nullable = false, updatable = false)
	private User createdBy;
	
	@Column(name = "updated_by", nullable = false)
	private User updatedBy;
	*/
}
