package com.goodstransport.user.controller;

import com.goodstransport.user.config.CustomUserDetailService;
import com.goodstransport.user.dto.JwtResponseDTO;
import com.goodstransport.user.dto.UserLoginDto;
import com.goodstransport.user.dto.UserRegisterDTO;
import com.goodstransport.user.model.User;
import com.goodstransport.user.security.JwtHelper;
import com.goodstransport.user.service.UserService;
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
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO)
    {
        User newUser = userService.registerUser(userRegisterDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login (@RequestBody UserLoginDto userLoginDto)
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

