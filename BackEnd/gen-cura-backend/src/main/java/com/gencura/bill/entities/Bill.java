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
	
	@Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal totalAmount;
	
	private BigDecimal discount = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode")
	private PaymentMode paymentMode;
	
	/*
	 * while the bill status is draft 
	 * all fields can be updated from services
	 * if it becomes the final result will be saved and not provided
	 * for further updates - 
	 * only possible when user-role Accountant can manage if required any update
	 * or done finalized by mistake.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "bill_status")
	private BillStatus status = BillStatus.DRAFT;
	
	/*
	 * JSONized list maintained instead of creating many entries
	 * to new Bill item table it may be repeated or will not be updatable
	 * if not provided proper access
	 * hence this will be maintained with JSON list - easy to access
	 */
	@Column(columnDefinition = "json")
    @Convert(converter = BillItemListConverter.class)
    private List<BillItem> items = new ArrayList<>();
	
	/*
	 * Custom Constructors are ** required **
	 * [can we manage this with DTOs?]
	 * for this bills table
	 * total amount is calculated and saved for further references
	 * if rateCardItems are updated in between then total amount 
	 * if calculated dynamically will be auto updated and may have some
	 * auditing issues. so saving it as new column is better choice.
	 */
	
	
}
