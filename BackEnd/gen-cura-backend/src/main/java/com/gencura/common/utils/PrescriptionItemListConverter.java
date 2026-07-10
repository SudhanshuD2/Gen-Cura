package com.gencura.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gencura.prescription.entities.PrescriptionItem;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PrescriptionItemListConverter 
    implements AttributeConverter<List<PrescriptionItem>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<PrescriptionItem> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting items to JSON", e);
        }
    }

    @Override
    public List<PrescriptionItem> convertToEntityAttribute(String json) {
        try {
            if (json == null) return new ArrayList<>();
            return objectMapper.readValue(json, 
                new TypeReference<List<PrescriptionItem>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to items", e);
        }
    }
}