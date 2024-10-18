package com.goodstransport.driver.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingDTO {
    private long bookingId;

    private Long userId;

    private Long driverId;

    private String pickupLocation;

    private String dropOffLocation;

    private LocalDateTime bookingTime;

    private Double estimatedCost;

    private BookingStatusDTO status;
}
