package com.gencura.catlog.entities;

import java.math.BigDecimal;

import com.gencura.common.entities.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="rate_card_entries")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name="rate_id"))
public class RateCardEntry extends BaseEntity{
	
	@Column(name = "service_name", nullable = false, precision = 10, scale = 2)
	private String serviceName;
	
	@Column(length = 500)
	private String description;
	
	@Column(nullable = false)
	private BigDecimal price;
	
}
