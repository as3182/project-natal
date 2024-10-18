package com.goodstransport.driver.dto;

import lombok.Data;

@Data
public class AskDriverForResponseDTO {
    private Long driverId;
    private Long bookingId;
    private String response;
}
