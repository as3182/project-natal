package com.goodstransport.user.controller;

import com.goodstransport.user.config.CustomUserDetailService;
import com.goodstransport.user.dto.*;
import com.goodstransport.user.model.User;
import com.goodstransport.user.security.JwtHelper;
import com.goodstransport.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/hi")
    public String hi()
    {
        return "hi!";
    }
    @PostMapping("/profile")
    public ResponseEntity<ProfileDTO> getUserProfile()
    {
        ProfileDTO profile = userService.getUserProfile();
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/bookings/{userId}")
    public Mono<ResponseEntity<List<BookingDTO>>> getUserBookings(@PathVariable Long userId) {


        Mono<List<BookingDTO>> userBookingsMono = webClientBuilder.build()
                .get()
                .uri("http://BOOKING-SERVICE/bookings/user/{userId}", userId)
                .retrieve()
                .bodyToFlux(BookingDTO.class)
                .collectList();

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

    @PostMapping("/broadcast")
    public Mono<String> broadcastToDrivers(@RequestBody BroadcastRequestDTO broadcastRequestDTO)
    {
        Mono<String>  response = webClientBuilder.build()
                .get()
                .uri("http://BOOKING-SERVICE/bookings/broadcastbooking")
                .retrieve()
                .bodyToMono(String.class);

        return response.onErrorResume(error->
        {
            return Mono.just("Error");
        });
    }










}

