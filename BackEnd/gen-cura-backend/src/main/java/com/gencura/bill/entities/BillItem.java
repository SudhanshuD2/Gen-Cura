package com.gencura.bill.entities;

import com.gencura.catlog.entities.RateCardEntry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItem {
	private RateCardEntry service;
	private Integer quantity;
}
