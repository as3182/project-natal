package com.goodstransport.driver.dto;

import com.goodstransport.driver.model.DriverStatus;
import lombok.Data;

@Data
public class ProfileDTO {
    private String phoneNumber;
    private String name;
    private String vehicleType;
    private String vehicleNumber;
    private String licenseNumber;
    private DriverStatus status;
    private Long vehicle;
}
