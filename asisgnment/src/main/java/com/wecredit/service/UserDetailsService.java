package com.wecredit.service;

import com.wecredit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        System.out.println("Inside loadUserByUsername");
        return userRepository.findUserByMobileNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid phoneNumber !"));
    }


}
