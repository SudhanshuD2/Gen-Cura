package com.gencura.bill.entities;

import java.math.BigDecimal;

import com.gencura.catlog.entities.RateCardEntry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItem {
	private Long rateCardEntryId;
	private BigDecimal unitPrice;
	private Integer quantity;
}
