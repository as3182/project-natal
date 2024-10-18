package com.goodstransport.booking.service;

import com.goodstransport.booking.dto.BroadcastRequestDTO;
import com.goodstransport.booking.dto.DriverLocationUpdateDTO;
import com.goodstransport.booking.dto.DriverStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BroadcastToDriverService implements BroadcastService{
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String DRIVER_LOCATION_TOPIC = "driver-location-updates";
    private static final String BOOKING_REQUEST_TOPIC = "driver-booking-requests";
    private static final double RADIUS_IN_KM = 5.0;
    private static final Logger log = LoggerFactory.getLogger(BroadcastToDriverService.class);

    private final Map<Long, DriverLocationUpdateDTO> map = new HashMap<>();


    @KafkaListener(topics=DRIVER_LOCATION_TOPIC,groupId = "booking_service_group")
    public void consumeDriverLocation(ConsumerRecord<String, DriverLocationUpdateDTO> record)
    {
        DriverLocationUpdateDTO locationUpdate = record.value();
        Long driverId = locationUpdate.getDriverId();
        map.put(driverId,locationUpdate);
    }

    public void broadcastBookingRequest(BroadcastRequestDTO broadcastRequest)
    {
        List<DriverLocationUpdateDTO> nearbyDrivers = getEligibleDrivers(broadcastRequest.getPickupLatitude(),broadcastRequest.getPickupLongitude(),broadcastRequest.getVehicleId());
        if(!nearbyDrivers.isEmpty())
        {
            for(DriverLocationUpdateDTO driver : nearbyDrivers)
            {
                kafkaTemplate.send(BOOKING_REQUEST_TOPIC,driver.getDriverId().toString(),broadcastRequest);
                System.out.println("Broadcasting booking request to Driver ID: " + driver.getDriverId());
            }
        }
        else {
            System.out.println("No drivers within 5km radius for this booking.");

        }
    }



    private List<DriverLocationUpdateDTO> getEligibleDrivers(double pickupLatitude , double pickupLongitude, long vehicleId)
    {
        List<DriverLocationUpdateDTO> nearbyDrivers = new ArrayList<>();
        for(DriverLocationUpdateDTO driver: map.values())
        {
            double distance = calculateDistanceInKm(pickupLatitude,pickupLongitude,driver.getLatitude(), driver.getLongitude());
            if(driver.getStatus().equals(DriverStatus.ONLINE) && vehicleId==driver.getVehicleId() && distance<=RADIUS_IN_KM)
            {
                nearbyDrivers.add(driver);
            }
            else
            {
                System.out.println(driver.getStatus());
                System.out.println(driver.getVehicleId());
                System.out.println(driver.getLatitude());
                System.out.println(driver.getLongitude());
                System.out.println(distance);
            }
        }
        return nearbyDrivers;
    }

    private double calculateDistanceInKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


}
