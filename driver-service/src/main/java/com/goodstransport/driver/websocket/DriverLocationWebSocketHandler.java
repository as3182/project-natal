package com.goodstransport.driver.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodstransport.driver.dto.DriverLocationUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DriverLocationWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final Map<String, WebSocketSession> driverSessions = new ConcurrentHashMap<>();


    private static final String LOCATION_UPDATE_TOPIC = "driver-location-updates";

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // Deserialize JSON message into DriverLocationUpdateDTO
        DriverLocationUpdateDTO locationUpdate = new ObjectMapper().readValue(payload, DriverLocationUpdateDTO.class);

        // Log the received driver location
        System.out.println("Received driver location update with driverId :  " + locationUpdate.getDriverId() +" VehicleId : " +locationUpdate.getVehicleId()+
                " Lat: " + locationUpdate.getLatitude() + " Lon: " + locationUpdate.getLongitude() + " Status : "+locationUpdate.getStatus());

        // Send the location update to Kafka
        kafkaTemplate.send(LOCATION_UPDATE_TOPIC, locationUpdate);

        // Optionally send an acknowledgment back to the WebSocket client
//        session.sendMessage(new TextMessage("Location updated"));
    }

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // Assuming driverId is part of WebSocket handshake or headers
//        String driverId = getDriverIdFromSession(session); // Implement this to extract the driverId
//        driverSessions.put(driverId, session);
//        System.out.println("Driver " + driverId + " connected to WebSocket.");
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // Remove the driver session on disconnect
//        String driverId = getDriverIdFromSession(session); // Extract the driverId
//        driverSessions.remove(driverId);
//        System.out.println("Driver " + driverId + " disconnected from WebSocket.");
//    }

    public void sendMessageToDriver(String driverId, String message) throws Exception {
        WebSocketSession session = driverSessions.get(driverId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        } else {
            System.out.println("Driver " + driverId + " is not connected to WebSocket.");
        }
    }

//    private String getDriverIdFromSession(WebSocketSession session) {
//        return (String) session.getAttributes().get("driverId");
//    }
}
