package com.goodstransport.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingDTO {
    private long bookingId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long driverId;

    @Column(nullable = false)
    private String pickupLocation;

    @Column(nullable = false)
    private String dropOffLocation;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private Double estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatusDTO status;
}
