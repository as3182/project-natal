package com.goodstransport.vehicles.controller;

import com.goodstransport.vehicles.model.Vehicles;
import com.goodstransport.vehicles.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicles> vehicleDetails(@PathVariable Long vehicleId) {
        Vehicles vehicleDetails = vehicleService.vehicleDetails(vehicleId);  // Assuming service returns a DTO

        if (vehicleDetails != null) {
            return ResponseEntity.ok(vehicleDetails);  // Return 200 OK with vehicle details
        } else {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if vehicle not found
        }
    }
}
