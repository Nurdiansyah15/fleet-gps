package com.nurdi.fleet_gps.utils.formatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseFormat<T> {
    private String message;
    private String status;
    private T data;
}
