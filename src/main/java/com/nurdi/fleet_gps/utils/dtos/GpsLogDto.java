package com.nurdi.fleet_gps.utils.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public class GpsLogDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestGpsLogDto {
        @NotNull
        private UUID vehicleId;

        @NotNull
        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        private Double latitude;

        @NotNull
        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        private Double longitude;

        @NotNull
        @PositiveOrZero
        private Double speed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseGpsLogDto {
        private UUID id;
        private UUID vehicleId;
        private Double latitude;
        private Double longitude;
        private Double speed;
        private LocalDateTime timestamp;
        private Boolean speedViolation;
    }


}
