package com.goodstransport.vehicles.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Vehicles {

    @Id
    private Long id;

    private double baseFare;
    private double capacity;
    private String dimensions;
    private double fareFor2To8Km;
    private double fareAbove8Km;
    private String vehicleType;
}
