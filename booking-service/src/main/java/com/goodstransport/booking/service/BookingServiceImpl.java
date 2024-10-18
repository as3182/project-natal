package com.goodstransport.booking.service;

import com.goodstransport.booking.dto.BookingDTO;
import com.goodstransport.booking.dto.VehicleDTO;
import com.goodstransport.booking.model.Booking;
import com.goodstransport.booking.model.BookingStatus;
import com.goodstransport.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = Booking.builder().
        userId(bookingDTO.getUserId()).
                driverId(bookingDTO.getDriverId()).
                pickupLocation(bookingDTO.getPickupLocation()).
                dropOffLocation(bookingDTO.getDropOffLocation()).
                estimatedCost(bookingDTO.getEstimatedCost()).
                status(BookingStatus.PENDING).
                bookingTime(LocalDateTime.now()).
                build();
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingByDriverId(Long driverId) {
        return bookingRepository.findByDriverId(driverId);
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, String status) {
        BookingStatus bookingStatus = BookingStatus.valueOf(status);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(bookingStatus);
        return bookingRepository.save(booking);
    }

    @Override
    public Double estimatedFare(VehicleDTO vehicleId, Double distance) {

        System.out.println("Vehicle Details: " + vehicleId.getBaseFare() +" 2-8 : "+vehicleId.getFareFor2To8Km()+" above8 : "+vehicleId.getFareAbove8Km()+" "+vehicleId.getDimensions()+" "+vehicleId.getCapacity()+" ");


        if(distance<=2)
        {
            return vehicleId.getBaseFare();
        }
        else if(distance>=2 && distance<=8)
        {
            return vehicleId.getBaseFare() + (vehicleId.getFareFor2To8Km()*(distance-2));
        }
        else
        {
            return vehicleId.getBaseFare() + (vehicleId.getFareAbove8Km()*(distance-2));
        }
    }

    @Override
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }


}
