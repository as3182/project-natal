package com.goodstransport.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodstransport.booking.dto.BookingDTO;
import com.goodstransport.booking.dto.BroadcastRequestDTO;
import com.goodstransport.booking.dto.FareDTO;
import com.goodstransport.booking.dto.VehicleDTO;
import com.goodstransport.booking.model.Booking;
import com.goodstransport.booking.model.BookingStatus;
import com.goodstransport.booking.service.BookingService;
import com.goodstransport.booking.service.BroadcastService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController
{

    private final WebClient.Builder webClientBuilder;


    @Autowired
    public BookingController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BroadcastService broadcastService;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

//    @GetMapping("/user/{userId}")
//    public Flux<ResponseEntity<Booking>> getBookingsByUserId(@PathVariable Long userId) {
//        List<Booking> bookings = bookingService.getBookingByUserId(userId);
//        return Flux.fromIterable(bookings)
//                .map(booking -> ResponseEntity.ok(booking));
//    }
@GetMapping("/user/{userId}")
public List<Booking> getBookingsByUserId(@PathVariable Long userId) {
    List<Booking> bookings = bookingService.getBookingByUserId(userId);
    return bookings;
}



//    @GetMapping("/driver/{driverId}")
//    public Flux<ResponseEntity<Booking>> getBookingsByDriverId(@PathVariable Long driverId) {
//        List<Booking> bookings =bookingService.getBookingByDriverId(driverId);
//        return Flux.fromIterable(bookings)
//                .map(booking -> ResponseEntity.ok(booking));
//    }

    @GetMapping("/driver/{driverId}")
    public List<Booking> getBookingsByDriverId(@PathVariable Long driverId) {
        List<Booking> bookings = bookingService.getBookingByUserId(driverId);
        return bookings;
    }
    @PutMapping("/updatestatus/{bookingId}")
    public Booking updateBookingStatus(@PathVariable Long bookingId, @RequestParam String status) {
        return bookingService.updateBookingStatus(bookingId, status);
    }

//    @GetMapping("/estimatefare")
//    public ResponseEntity<Double> estimateFare(@RequestBody FareDTO fareDTO) {
//        Long vehicleId = fareDTO.getVehicleId();
//        Double distance = fareDTO.getDistance();
//
//        Double amount = Double.valueOf(df.format(bookingService.estimatedFare(vehicleId, distance)));
//        return ResponseEntity.ok(amount);
//    }

    @GetMapping("/estimatefare")
    public Mono<ResponseEntity<Double>> estimateFare(@RequestBody FareDTO fareDTO) {
        Long vehicleId = fareDTO.getVehicleId();
        Double distance = fareDTO.getDistance();

        Mono<VehicleDTO> vehicleDetailsMono = webClientBuilder.build()
                .get()
                .uri("http://VEHICLE-SERVICE/vehicle/{vehicleId}", vehicleId)  // Correctly using the URI variable
                .retrieve()
                .bodyToMono(VehicleDTO.class);

        return vehicleDetailsMono
                .map(vehicleDetails -> {
                    Double estimatedFare = bookingService.estimatedFare(vehicleDetails, distance);
                    return ResponseEntity.ok(estimatedFare);
                })
                .onErrorResume(error -> {
                    // Handle any error (e.g., vehicle service failure) and return a fallback response
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(null));
                });
    }

    @PostMapping("/broadcastbooking")
    public String broadcastBooking(@RequestBody BroadcastRequestDTO request) throws JsonProcessingException {
        broadcastService.broadcastBookingRequest(request);
        return "Broadcast sent";
    }



    @GetMapping("/allbookings")
    public List<Booking> getAllBookings()
    {
        return bookingService.getAllBookings();
    }






}

