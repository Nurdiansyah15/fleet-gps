package com.nurdi.fleet_gps.utils.dtos;

import com.nurdi.fleet_gps.models.GpsLog;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class VehicleDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RequestVehicleDto {
        @NotBlank
        private String plateNumber;

        @NotBlank
        private String name;

        @NotBlank
        private String type;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseVehicleDto {
        private UUID id;
        private String plateNumber;
        private String name;
        private String type;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseVehicleLocationDto {
        private UUID vehicleId;
        private String plateNumber;
        private String name;
        private String type;
        private Location location;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Location {
            private Double latitude;
            private Double longitude;
            private Double speed;
            private LocalDateTime timestamp;
            private Boolean isViolation;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseVehicleHistoryDto {
        private UUID vehicleId;
        private String plateNumber;
        private String name;
        private String type;
        private List<GpsLogDto> logs;
        private PaginationInfo pagination;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class GpsLogDto {
            private Double latitude;
            private Double longitude;
            private Double speed;
            private Boolean isViolation;
            private LocalDateTime timestamp;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class PaginationInfo {
            private int currentPage;
            private int totalPages;
            private long totalItems;
            private int pageSize;
        }
    }
}
