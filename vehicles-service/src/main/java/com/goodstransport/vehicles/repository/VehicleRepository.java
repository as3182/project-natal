package com.goodstransport.vehicles.repository;

import com.goodstransport.vehicles.model.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicles,Long> {
}
