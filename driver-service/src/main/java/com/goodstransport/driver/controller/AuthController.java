package com.goodstransport.driver.controller;

import com.goodstransport.driver.config.CustomUserDetailService;
import com.goodstransport.driver.dto.JwtResponseDTO;
import com.goodstransport.driver.dto.DriverLoginDTO;
import com.goodstransport.driver.dto.DriverRegisterDTO;
import com.goodstransport.driver.model.Driver;
import com.goodstransport.driver.security.JwtHelper;
import com.goodstransport.driver.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")

public class AuthController {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<Driver> registerUser(@Valid @RequestBody DriverRegisterDTO userRegisterDTO)
    {
        Driver newUser = driverService.registerUser(userRegisterDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login (@RequestBody DriverLoginDTO userLoginDto)
    {
        this.doAuthenticate(userLoginDto.getPhoneNumber(),userLoginDto.getPassword());

        UserDetails userDetails = userDetailService.loadUserByUsername(userLoginDto.getPhoneNumber());
        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponseDTO response = JwtResponseDTO.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
        try{
            authenticationManager.authenticate(authentication);}
        catch(BadCredentialsException e)
        {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler()
    {
        return "Invalid Credentials";
    }
}

