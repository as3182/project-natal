package com.goodstransport.user.service;

import com.goodstransport.user.dto.ProfileDTO;
import com.goodstransport.user.dto.UserRegisterDTO;
import com.goodstransport.user.model.User;
import com.goodstransport.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User registerUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setName(userRegisterDTO.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        userRepository.save(user);
        return user;
    }

    @Override
    public ProfileDTO getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = getPhoneNumberFromSecurityContext();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("Not Found"));
        ProfileDTO userProfile = new ProfileDTO();
        userProfile.setEmail(user.getEmail());
        userProfile.setName(user.getName());
        userProfile.setPhoneNumber(user.getPhoneNumber());
        return userProfile;
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
