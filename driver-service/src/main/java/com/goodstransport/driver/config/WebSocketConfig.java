package com.goodstransport.driver.config;

import com.goodstransport.driver.websocket.DriverLocationWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final DriverLocationWebSocketHandler driverLocationWebSocketHandler;

    public WebSocketConfig(DriverLocationWebSocketHandler driverLocationWebSocketHandler) {
        this.driverLocationWebSocketHandler = driverLocationWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(driverLocationWebSocketHandler, "/ws").setAllowedOrigins("*");
    }
}


