package com.gencura.bill.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gencura.appointment.entities.Appointment;
import com.gencura.common.entities.BaseEntity;
import com.gencura.common.enums.BillStatus;
import com.gencura.common.enums.PaymentMode;
import com.gencura.common.utils.BillItemListConverter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="id", column = @Column(name="bill_id"))
public class Bill extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;
	
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	
	private BigDecimal discount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode")
	private PaymentMode paymentMode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "bill_status")
	private BillStatus status = BillStatus.FINALIZED;
	
	@Column(columnDefinition = "json")
    @Convert(converter = BillItemListConverter.class)
    private List<BillItem> items = new ArrayList<>();
}
