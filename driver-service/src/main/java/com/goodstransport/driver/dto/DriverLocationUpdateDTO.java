package com.goodstransport.driver.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goodstransport.driver.model.DriverStatus;
import lombok.Data;

@Data
@JsonSerialize
public class DriverLocationUpdateDTO {
    private Long driverId;
    private Long vehicleId;
    private DriverStatus status;
    private double latitude;
    private double longitude;
}