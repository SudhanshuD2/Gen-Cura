package com.gencura.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencura.bill.entities.BillItem;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BillItemListConverter implements AttributeConverter<List<BillItem>, String>{
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public String convertToDatabaseColumn(List<BillItem> attribute) {
		try {
			
			return objectMapper.writeValueAsString(attribute);
		}catch(JsonProcessingException e) {
			throw new RuntimeException("Error: Converting to string is not possible", e);
		}
	}
	
	@Override
	public List<BillItem> convertToEntityAttribute(String dbData) {
		try {
            if (dbData == null) return new ArrayList<>();
            
            return objectMapper.readValue(dbData, new TypeReference<List<BillItem>>() {});
        
		} catch (JsonProcessingException e) {
           
			throw new RuntimeException("Error converting JSON to items", e);
        }
	}
}
