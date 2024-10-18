package com.goodstransport.booking.service;

import com.goodstransport.booking.dto.BookingDTO;
import com.goodstransport.booking.dto.VehicleDTO;
import com.goodstransport.booking.model.Booking;
import com.goodstransport.booking.model.BookingStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    public Booking createBooking(BookingDTO bookingDTO);
    public List<Booking> getBookingByUserId(Long userId);
    public List<Booking> getBookingByDriverId( Long driverId);
    public Booking updateBookingStatus(Long bookingId, String status);


//    public Double estimatedFare(Long vehicleId, Double distance);
    public Double estimatedFare(VehicleDTO vehicleId, Double distance);

    public List<Booking> getAllBookings();

}
