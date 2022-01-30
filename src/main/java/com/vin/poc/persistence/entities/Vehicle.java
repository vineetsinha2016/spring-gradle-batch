package com.vin.poc.persistence.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Vehicle")
@Data
public class Vehicle {
    @Id
    @Column(name = "vehicleid", nullable = false)
    private String vehicleId;
    @Column(name = "VIN")
    private String VIN;
    @Column(name = "model")
    private String model;
    @Column(name = "year")
    private String year;
    @Column(name = "color")
    private String color;
    @Column(name = "company")
    private String company;
    @Column(name = "category")
    private String category;
}
