package com.nurdi.fleet_gps.services;

import com.nurdi.fleet_gps.models.GpsLog;
import com.nurdi.fleet_gps.models.Vehicle;
import com.nurdi.fleet_gps.repositories.GpsLogRepository;
import com.nurdi.fleet_gps.repositories.VehicleRepository;
import com.nurdi.fleet_gps.utils.dtos.VehicleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private GpsLogRepository gpsLogRepository;

    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    public Vehicle getVehicleById(UUID id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
    }

    public Vehicle createVehicle(VehicleDto.RequestVehicleDto req) {
        Vehicle v = Vehicle.builder()
                .plateNumber(req.getPlateNumber())
                .name(req.getName())
                .type(req.getType())
                .build();
        return vehicleRepository.save(v);
    }

    public Vehicle updateVehicle(UUID id, VehicleDto.RequestVehicleDto req) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        v.setPlateNumber(req.getPlateNumber());
        v.setName(req.getName());
        v.setType(req.getType());
        return vehicleRepository.save(v);
    }

    public void deleteVehicle(UUID id) {
        vehicleRepository.deleteById(id);
    }

    public VehicleDto.ResponseVehicleLocationDto getLastLocation(UUID vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));


        Optional<GpsLog> latestLog = gpsLogRepository.findFirstByVehicleIdOrderByTimestampDesc(vehicleId);

        return VehicleDto.ResponseVehicleLocationDto.builder()
                .vehicleId(vehicle.getId())
                .plateNumber(vehicle.getPlateNumber())
                .name(vehicle.getName())
                .type(vehicle.getType())
                .location(latestLog.map(log ->
                        VehicleDto.ResponseVehicleLocationDto.Location.builder()
                                .latitude(log.getLatitude())
                                .longitude(log.getLongitude())
                                .speed(log.getSpeed())
                                .isViolation(log.getSpeedViolation())
                                .timestamp(log.getTimestamp())
                                .build()
                ).orElse(null))
                .build();
    }

    public VehicleDto.ResponseVehicleHistoryDto getHistory(UUID vehicleId, LocalDateTime from, LocalDateTime to, Pageable pageable) {

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));


        Page<GpsLog> logs = gpsLogRepository.findByVehicleIdAndTimestampBetween(
                vehicleId,
                from,
                to,
                pageable
        );

        return VehicleDto.ResponseVehicleHistoryDto.builder()
                .vehicleId(vehicle.getId())
                .plateNumber(vehicle.getPlateNumber())
                .name(vehicle.getName())
                .type(vehicle.getType())
                .logs(logs.getContent().stream()
                        .map(this::mapToLogDto)
                        .collect(Collectors.toList()))
                .pagination(VehicleDto.ResponseVehicleHistoryDto.PaginationInfo.builder()
                        .currentPage(logs.getNumber())
                        .totalPages(logs.getTotalPages())
                        .totalItems(logs.getTotalElements())
                        .pageSize(logs.getSize())
                        .build())
                .build();
    }

    private VehicleDto.ResponseVehicleHistoryDto.GpsLogDto mapToLogDto(GpsLog log) {
        return VehicleDto.ResponseVehicleHistoryDto.GpsLogDto.builder()
                .latitude(log.getLatitude())
                .longitude(log.getLongitude())
                .speed(log.getSpeed())
                .isViolation(log.getSpeedViolation())
                .timestamp(log.getTimestamp())
                .build();


    }
}
