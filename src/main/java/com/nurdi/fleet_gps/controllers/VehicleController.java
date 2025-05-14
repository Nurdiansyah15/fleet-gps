package com.nurdi.fleet_gps.controllers;

import com.nurdi.fleet_gps.services.VehicleService;
import com.nurdi.fleet_gps.utils.dtos.SwaggerDto;
import com.nurdi.fleet_gps.utils.dtos.VehicleDto;
import com.nurdi.fleet_gps.utils.formatter.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Operation(summary = "Get All", description = "Get all vehicles", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<?> getAll(

            @Parameter(
                    description = "Pagination parameters",
                    example = "{\"page\":0,\"size\":10,\"sort\":\"id,desc\"}"
            )
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseBuilder.renderPageableJSON(vehicleService.getAllVehicles(pageable), "Success", HttpStatus.OK);
    }

    @Operation(summary = "Get One", description = "Get one vehicle", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        return ResponseBuilder.buildResponse(vehicleService.getVehicleById(id), "Success", HttpStatus.OK);
    }

    @Operation(summary = "Create", description = "Create vehicle", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VehicleDto.RequestVehicleDto req) {
        return ResponseBuilder.buildResponse(vehicleService.createVehicle(req), "Created", HttpStatus.CREATED);
    }

    @Operation(summary = "Update", description = "Update vehicle", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody VehicleDto.RequestVehicleDto req) {
        return ResponseBuilder.buildResponse(vehicleService.updateVehicle(id, req), "Updated", HttpStatus.OK);
    }

    @Operation(summary = "Delete", description = "Delete vehicle", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        vehicleService.deleteVehicle(id);
        return ResponseBuilder.buildResponse(null, "Deleted", HttpStatus.OK);
    }

    @Operation(summary = "Get Last Location", description = "Get last location", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}/locations")
    public ResponseEntity<?> lastLocation(@PathVariable UUID id) {
        return ResponseBuilder.buildResponse(vehicleService.getLastLocation(id), "Success", HttpStatus.OK);
    }

    @Operation(summary = "Get History", description = "Get history", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SwaggerDto.WebResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}/histories")
    public ResponseEntity<?> history(@PathVariable UUID id,

                                     @RequestParam(
                                             defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(7)}",
                                             required = false
                                     )
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                     @Parameter(
                                             description = "Start datetime (ISO format)",
                                             example = "2025-01-01T00:00:00"
                                     )
                                     LocalDateTime from,

                                     @RequestParam(
                                             defaultValue = "#{T(java.time.LocalDateTime).now()}",
                                             required = false
                                     )
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                     @Parameter(
                                             description = "End datetime (ISO format)",
                                             example = "2025-12-31T23:59:59"
                                     )
                                     LocalDateTime to,

                                     @Parameter(
                                             description = "Pagination parameters",
                                             example = "{\"page\":0,\"size\":10,\"sort\":\"id,desc\"}"
                                     )
                                     @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                                     Pageable pageable) {
        return ResponseBuilder.buildResponse(vehicleService.getHistory(id, from, to, pageable), "Success", HttpStatus.OK);
    }
}
