package com.goodstransport.driver.config;


import com.goodstransport.driver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService
{
    @Autowired
    private DriverRepository driverRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return driverRepository.findByPhoneNumber(username).orElseThrow(()-> new UsernameNotFoundException("NOT FOUND"));
    }
}
