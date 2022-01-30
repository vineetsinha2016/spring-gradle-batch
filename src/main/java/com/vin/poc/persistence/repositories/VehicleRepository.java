package com.vin.poc.persistence.repositories;

import com.vin.poc.persistence.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
