package com.goodstransport.driver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DriverRegisterDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "Name is required")
    private String vehicleNumber;

    @NotBlank(message = "Name is required")
    private String licenseNumber;

    @NotBlank(message = "Vehicle type is required")
    private Long vehicleId;
}
