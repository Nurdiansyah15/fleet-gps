package com.nurdi.fleet_gps.seeders;

import com.nurdi.fleet_gps.models.GpsLog;
import com.nurdi.fleet_gps.models.Vehicle;
import com.nurdi.fleet_gps.repositories.GpsLogRepository;
import com.nurdi.fleet_gps.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final VehicleRepository vehicleRepo;
    private final GpsLogRepository gpsLogRepo;

    public DatabaseSeeder(VehicleRepository vehicleRepo,
                          GpsLogRepository gpsLogRepo) {
        this.vehicleRepo = vehicleRepo;
        this.gpsLogRepo = gpsLogRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (vehicleRepo.count() > 0) {
            return;
        }

        Vehicle truck = new Vehicle();
        truck.setPlateNumber("B 1234 ABC");
        truck.setName("Truck Angkut Barang");
        truck.setType("TRUCK");

        Vehicle car = new Vehicle();
        car.setPlateNumber("B 5678 XYZ");
        car.setName("Mobil Presiden");
        car.setType("CAR");

        vehicleRepo.saveAll(List.of(truck, car));

        LocalDateTime now = LocalDateTime.now();

        if (gpsLogRepo.count() > 0) {
            return;
        }

        GpsLog log1 = new GpsLog();
        log1.setVehicle(truck);
        log1.setLatitude(-6.1754);
        log1.setLongitude(106.8272);
        log1.setSpeed(60.0);
        log1.setTimestamp(now.minusHours(1));
        log1.setSpeedViolation(false);

        GpsLog log2 = new GpsLog();
        log2.setVehicle(truck);
        log2.setLatitude(-6.1800);
        log2.setLongitude(106.8300);
        log2.setSpeed(120.0);
        log2.setTimestamp(now.minusMinutes(30));
        log2.setSpeedViolation(true);

        GpsLog log3 = new GpsLog();
        log3.setVehicle(car);
        log3.setLatitude(-6.1850);
        log3.setLongitude(106.8350);
        log3.setSpeed(70.0);
        log3.setTimestamp(now.minusHours(2));
        log3.setSpeedViolation(false);


        gpsLogRepo.saveAll(List.of(log1, log2, log3));
    }
}