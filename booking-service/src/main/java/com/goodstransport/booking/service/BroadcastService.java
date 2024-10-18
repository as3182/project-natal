package com.goodstransport.booking.service;

import com.goodstransport.booking.dto.BroadcastRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface BroadcastService {
    public void broadcastBookingRequest(BroadcastRequestDTO broadcastRequest);
}
