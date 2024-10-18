package com.goodstransport.vehicles.service;

import com.goodstransport.vehicles.model.Vehicles;
import com.goodstransport.vehicles.repository.VehicleRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Override
    public Vehicles vehicleDetails(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(()->new RuntimeException("Not Found"));
    }
}
