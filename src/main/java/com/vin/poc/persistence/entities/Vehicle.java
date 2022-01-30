package com.vin.poc.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Vehicle")
public class Vehicle {
    @Id
    @Column(name = "vehicleId", nullable = false)
    private String vehicleId;
    private String VIN;
    private String model;
    private String year;
    private String color;
    private String company;
    private String category;
}
