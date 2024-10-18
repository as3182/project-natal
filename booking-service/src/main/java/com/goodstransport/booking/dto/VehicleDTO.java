package com.goodstransport.booking.dto;

import lombok.Data;

@Data
public class VehicleDTO {

    private Long id;
    private double baseFare;
    private double capacity;
    private String dimensions;
    private double fareFor2To8Km;
    private double fareAbove8Km;
    private String vehicleType;
}
