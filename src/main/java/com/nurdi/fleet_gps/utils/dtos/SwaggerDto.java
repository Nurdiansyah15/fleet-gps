package com.nurdi.fleet_gps.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

public class SwaggerDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class WebResponse<T> {
        private String message;
        private HttpStatus status;
        private T data;
    }
}
