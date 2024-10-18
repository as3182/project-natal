package com.goodstransport.vehicles.service;

import com.goodstransport.vehicles.model.Vehicles;
import org.springframework.stereotype.Service;

@Service
public interface VehicleService {

    public Vehicles vehicleDetails(Long vehicleId);
}
