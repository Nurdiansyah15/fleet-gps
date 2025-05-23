package com.nurdi.fleet_gps.repositories;

import com.nurdi.fleet_gps.models.GpsLog;
import com.nurdi.fleet_gps.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GpsLogRepository extends JpaRepository<GpsLog, UUID> {

    Page<GpsLog> findByVehicleIdAndTimestampBetween(UUID vehicleId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<GpsLog> findFirstByVehicleIdOrderByTimestampDesc(UUID vehicleId);

}

