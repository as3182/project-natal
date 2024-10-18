//package com.goodstransport.driver.kafka;
//
//import com.goodstransport.driver.dto.BroadcastRequestDTO;
//import com.goodstransport.driver.util.JwtUtil;
//import com.goodstransport.driver.websocket.DriverLocationWebSocketHandler;
//import jakarta.servlet.http.HttpServletRequest;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BookingConsumer {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private DriverLocationWebSocketHandler webSocketHandler;
//
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    private static final String BOOKING_REQUEST_TOPIC = "driver-booking-requests";
//
//
//    @KafkaListener(topics = BOOKING_REQUEST_TOPIC, groupId = "driver-service-group")
//    public void listenForBookingRequest(ConsumerRecord<String, BroadcastRequestDTO> record) {
//        String driverId = record.key();
//        BroadcastRequestDTO bookingRequest = record.value();
//
//        //from jwt token
//        String currentDriverId = getCurrentDriverId();
//
//        if(driverId.equals(currentDriverId))
//        {
//            System.out.println("Driver " + currentDriverId + " received a booking request: " + bookingRequest);
//
//            try {
//                // Notify the frontend or mobile app via WebSocket
//                webSocketHandler.sendMessageToDriver(currentDriverId, "Booking Request: " + bookingRequest);
//            } catch (Exception e) {
//                System.out.println("Error sending WebSocket message to driver: " + e.getMessage());
//            }
//        }
//
//
//    }
//
//
//        public String getCurrentDriverId() {
//            String token = httpServletRequest.getHeader("Authorization").substring(7); // Remove 'Bearer ' from token
//            return jwtUtil.extractDriverId(token);
//        }
//    }
//
//
//
