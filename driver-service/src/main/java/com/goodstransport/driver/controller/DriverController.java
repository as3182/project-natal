package com.goodstransport.driver.controller;


import com.goodstransport.driver.dto.AskDriverForResponseDTO;
import com.goodstransport.driver.dto.BookingDTO;
import com.goodstransport.driver.dto.ProfileDTO;
import com.goodstransport.driver.model.DriverStatus;
import com.goodstransport.driver.service.DriverService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpHeaders;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {


    @Autowired
    private DriverService userService;


    private final WebClient.Builder webClientBuilder;

    @Autowired
    public DriverController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileDTO> getUserProfile()
    {
        ProfileDTO profile = userService.getUserProfile();
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/bookings/{driverId}")
    public Mono<ResponseEntity<List<BookingDTO>>> getUserBookings(@PathVariable Long driverId) {

//        System.out.println("Authorization Header: " + authHeader);

        Mono<List<BookingDTO>> userBookingsMono = webClientBuilder.build()
                .get()
                .uri("http://BOOKING-SERVICE/bookings/driver/{driverId}", driverId)
                .retrieve()
                .bodyToFlux(BookingDTO.class)
                .collectList(); // Collecting the list of BookingDTO

        return userBookingsMono.map(bookings -> {
                    // Wrap the result in ResponseEntity
                    return ResponseEntity.ok(bookings);
                })
                .defaultIfEmpty(ResponseEntity.noContent().build()) // Handle empty response
                .onErrorResume(error -> {
                    // Log the error response
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webClientResponseException = (WebClientResponseException) error;
                        System.err.println("Error fetching user bookings: " + webClientResponseException.getRawStatusCode() + " - " + webClientResponseException.getResponseBodyAsString());
                    } else {
                        System.err.println("Error fetching user bookings: " + error.getMessage());
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

//    @GetMapping("/bookings/{driverId}")
//    public List<BookingDTO> getDriverBookings(@PathVariable Long driverId) {
//        return webClientBuilder.build()
//                .get()
//                .uri("http://BOOKING-SERVICE/bookings/driver/" + driverId)
//                .retrieve()
//                .bodyToFlux(BookingDTO.class)
//                .collectList()
//                .block();
//    }


    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@PathVariable DriverStatus driverStatus)
    {
        return userService.updateStatus(driverStatus);
    }

    @PostMapping("/respond")
    public Mono<BookingDTO> respondToBookingRequest(@RequestBody AskDriverForResponseDTO responseDTO)
    {
        if(responseDTO.getResponse().equalsIgnoreCase("DECLINED"))
        {
            return null;
        }
        else if(responseDTO.getResponse().equalsIgnoreCase("ACCEPTED"))
        {
            userService.respondToBookingRequest(responseDTO);
           Long bookingId = responseDTO.getBookingId();
           Long driverId = responseDTO.getDriverId();

            Mono<BookingDTO> bookingReceipt = webClientBuilder.build()
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("BOOKING-SERVICE")
                            .path("/bookings/updatestatus/{driverId}")    // Path variable
                            .queryParam("bookingId", bookingId)           // Query parameter
                            .build(driverId))
                    .retrieve()
                    .bodyToMono(BookingDTO.class);

            return bookingReceipt;


        }
        else{return null;}

    }




}

