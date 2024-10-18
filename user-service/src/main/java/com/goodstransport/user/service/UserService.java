package com.goodstransport.user.service;

import com.goodstransport.user.dto.ProfileDTO;
import com.goodstransport.user.dto.UserRegisterDTO;
import com.goodstransport.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public User registerUser(UserRegisterDTO userRegisterDTO);

    public ProfileDTO getUserProfile();
}
