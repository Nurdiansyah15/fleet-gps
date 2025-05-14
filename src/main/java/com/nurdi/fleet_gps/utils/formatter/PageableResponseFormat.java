package com.nurdi.fleet_gps.utils.formatter;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponseFormat<T> {
    private String message;
    private String status;
    private List<T> items;
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;
}
