package com.goodstransport.driver.service;

import com.goodstransport.driver.dto.AskDriverForResponseDTO;
import com.goodstransport.driver.model.Driver;
import com.goodstransport.driver.model.DriverStatus;
import com.goodstransport.driver.repository.DriverRepository;
import com.goodstransport.driver.dto.ProfileDTO;
import com.goodstransport.driver.dto.DriverRegisterDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Driver registerUser(DriverRegisterDTO userRegisterDTO) {
        Driver user = new Driver();
        user.setName(userRegisterDTO.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setLicenseNumber(userRegisterDTO.getLicenseNumber());
        user.setVehicleNumber(userRegisterDTO.getVehicleNumber());
        user.setStatus(DriverStatus.ONLINE);
        user.setVehicleId(userRegisterDTO.getVehicleId());
        driverRepository.save(user);
        return user;
    }

    @Override
    public ProfileDTO getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = getPhoneNumberFromSecurityContext();
        Driver user = driverRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("Not Found"));
        ProfileDTO userProfile = new ProfileDTO();
        userProfile.setName(user.getName());
        userProfile.setPhoneNumber(user.getPhoneNumber());
        userProfile.setLicenseNumber(user.getLicenseNumber());
        userProfile.setVehicleNumber(user.getVehicleNumber());
        userProfile.setStatus(user.getStatus());
        userProfile.setVehicle(user.getVehicleId());
        return userProfile;
    }

    @Override
    public ResponseEntity<?> updateStatus(DriverStatus driverStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = getPhoneNumberFromSecurityContext();
        Driver driver = driverRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("Not Found"));
        driver.setStatus(driverStatus);
        driverRepository.save(driver);
        return ResponseEntity.ok("Updated");
    }

    @Override
    public void respondToBookingRequest(AskDriverForResponseDTO responseDTO) {
        updateStatus(DriverStatus.BUSY);



    }

    private String getPhoneNumberFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // Get the phone number from the UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else {
            throw new RuntimeException("Auth principal is not a recognized user");
        }
    }


    private String extractUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("rivbreijvnirejnfvirnviourhviunriovnrionfvirnviuhrniuhUJBODNHOIDHIYDBHUJDHVUDIUDHIHDVBOJDVIDHYBDJKDBVUDHBJDVHVDBDJODNBV47r9834yr98yr9rg483g9b4ef9g4rf87grfih498fh")
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }



}
