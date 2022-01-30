package com.vin.poc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private String vehicleId;
    private String VIN;
    private String model;
    private String year;
    private String color;
    private String company;
    private String category;
}
