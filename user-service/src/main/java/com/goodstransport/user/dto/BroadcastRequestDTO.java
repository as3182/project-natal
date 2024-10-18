package com.goodstransport.user.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class BroadcastRequestDTO {
    private String pickupLocation;      // e.g., "Sector 62, Noida"
    private String dropoffLocation;     // e.g., "Sector 18, Noida"
    private Long vehicleId;              // Unique identifier for the selected vehicle
    private Long userId;                 // Unique identifier for the user (optional)
    private Double pickupLatitude;       // Latitude of the pickup location
    private Double pickupLongitude;      // Longitude of the pickup location
    private Double dropoffLatitude;      // Latitude of the dropoff location
    private Double dropoffLongitude;     // Longitude of the dropoff location
    private Instant timestamp;           // Timestamp of the request (optional)
    private Double distance;             // Distance calculated (optional)

}
