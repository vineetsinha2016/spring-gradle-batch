package com.vin.poc.processor;

import com.vin.poc.model.VehicleDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class VehicleFieldSetMapper implements FieldSetMapper<VehicleDTO> {
    @Override
    public VehicleDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        String vehicleId = fieldSet.readRawString("vehicleId");
        String VIN = fieldSet.readRawString("VIN");
        String model = fieldSet.readRawString("model");
        String year = fieldSet.readRawString("year");
        String color = fieldSet.readRawString("color");
        String company = fieldSet.readRawString("company");
        String category = fieldSet.readRawString("category");
        return new VehicleDTO(vehicleId, VIN, model, year, color, company, category);
    }
}
