//package com.goodstransport.driver.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.goodstransport.driver.dto.BroadcastRequestDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookingListener {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @KafkaListener(topics = "driver_broadcast", groupId = "driver_group")
//    public void listenBookingBroadcast(String message) throws JsonProcessingException {
//        // Deserialize the incoming message to BroadcastRequestDto
//        BroadcastRequestDTO request = new ObjectMapper().readValue(message, BroadcastRequestDTO.class);
//
//
//        // Check if the driver is within the 5 km range
//        if (isWithinRange(driverLongitude, driverLatitude, request.getPickupLongitude(), request.getPickupLatitude())) {
//            String driverId = "driverId"; // Replace with actual driver ID logic
//            // Send acceptance to the Kafka driver response topic
//            kafkaTemplate.send("driver_response", "DriverId: " + driverId + " accepted the request for pickup at " + request.getPickupLocation());
//        } else {
//            // Optionally, send a message indicating that the driver is out of range
//            kafkaTemplate.send("driver_response", "Drivers are out of range for pickup.");
//        }
//    }
//
//    private boolean isWithinRange(Double driverLongitude, Double driverLatitude, Double pickupLongitude, Double pickupLatitude) {
//        // Calculate the distance between the driver's location and the pickup location.
//        // You can use the Haversine formula or any other method to calculate the distance.
//        double distance = calculateDistance(driverLatitude, driverLongitude, pickupLatitude, pickupLongitude);
//        return distance <= 5.0; // Assuming distance is in kilometers
//    }
//
//    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
//        // Haversine formula to calculate distance between two points on the earth specified in decimal degrees
//        final int R = 6371; // Radius of the earth in km
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c; // Convert to kilometers
//        return distance;
//    }
//
//    // Mock methods to get current driver location
////    private Double getCurrentDriverLongitude() {
////        // Logic to get the driver's current longitude
////        return 77.12345; // Replace with actual logic
////    }
////
////    private Double getCurrentDriverLatitude() {
////        // Logic to get the driver's current latitude
////        return 28.54321; // Replace with actual logic
////    }
//}
