package com.goodstransport.booking.dto;

import com.goodstransport.booking.model.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingDTO
{
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long driverId;

    @Column(nullable = false)
    private String pickupLocation;

    @Column(nullable = false)
    private String dropOffLocation;

    @Column(nullable = false)
    private Double estimatedCost;

}
