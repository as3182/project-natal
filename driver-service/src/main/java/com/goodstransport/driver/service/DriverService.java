package com.goodstransport.driver.service;

import com.goodstransport.driver.dto.AskDriverForResponseDTO;
import com.goodstransport.driver.dto.ProfileDTO;
import com.goodstransport.driver.dto.DriverRegisterDTO;
import com.goodstransport.driver.model.Driver;
import com.goodstransport.driver.model.DriverStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface DriverService {
    public Driver registerUser(DriverRegisterDTO userRegisterDTO);

    public ProfileDTO getUserProfile();

    public ResponseEntity<?> updateStatus(DriverStatus driverStatus);

    public void respondToBookingRequest(AskDriverForResponseDTO responseDTO);
}
