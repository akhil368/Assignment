package com.wecredit.service;


import com.wecredit.config.JwtUtil;
import com.wecredit.dto.UserDetailsDto;
import com.wecredit.dto.UserMapper;
import com.wecredit.exceptions.NotFoundException;
import com.wecredit.model.User;
import com.wecredit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public User saveUser(User user) {
        if(user==null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);

    }
    public User findUserByPhoneNumber(String phoneNumber) {
      Optional<User> opt= userRepository.findUserByMobileNumber(phoneNumber);
        return opt.orElse(null);
    }

    public UserDetailsDto getCurrentUserDetails(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token= token.substring(7);
        }
        String userName=jwtUtil.extractUsername(token);
         User user=userRepository.findUserByMobileNumber(userName).orElseThrow(()->new NotFoundException("User not found"));
        return UserMapper.mapToUserDetailsDto(user);
    }

}
