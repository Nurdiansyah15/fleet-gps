package com.nurdi.fleet_gps.utils.formatter;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseFormat<T> {
    private String status;
    private T error;
}