package com.nurdi.fleet_gps.repositories;

import com.nurdi.fleet_gps.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

}
