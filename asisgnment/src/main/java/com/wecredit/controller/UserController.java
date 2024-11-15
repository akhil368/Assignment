package com.wecredit.controller;

import com.wecredit.config.OTPAuthenticationProvider;
import com.wecredit.config.OTPAuthenticationToken;
import com.wecredit.dto.UserDetailsDto;
import com.wecredit.dto.UserMapper;
import com.wecredit.model.User;
import com.wecredit.service.OtpService;
import com.wecredit.service.UserDetailsService;
import com.wecredit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private OTPAuthenticationProvider authenticationManager;


    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(HttpServletRequest request, @RequestBody UserDetailsDto userDetailsDto) {
        try {
             User user= UserMapper.mapToUser(userDetailsDto);
             String ipAddress=authenticationManager.getClientIP(request);
             user.setDeviceID(ipAddress);
             UserDetails isExistingUser=userService.findUserByPhoneNumber(userDetailsDto.getMobileNumber());
             if(Objects.nonNull(isExistingUser)) {
                 return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
             }
             User savedUser=userService.saveUser(user);
            return new ResponseEntity<>(UserMapper.mapToUserDetailsDto(savedUser),HttpStatus.CREATED);
        } catch (Exception ex) {
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/{phoneNumber}/{otp}")
    public ResponseEntity<?> loginUser(@PathVariable String phoneNumber,@PathVariable Integer otp,HttpServletRequest request) {
        try {
            OTPAuthenticationToken token = new OTPAuthenticationToken(phoneNumber, otp);
            Authentication authentication = authenticationManager.authenticate(token);
            String jwtToken = (String) authentication.getCredentials();

            Map<String, String> response = new HashMap<>();
            response.put("message", "User Login Successful");
            response.put("token", jwtToken);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCurrentUserDetails")
    public ResponseEntity<UserDetailsDto> getCurrentUser(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            return new ResponseEntity<>(userService.getCurrentUserDetails(bearerToken),HttpStatus.OK);
        } catch (Exception ex) {
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/test")
    public String sayHello()   {
        return "Hello";
    }

    @PostMapping("/getUser/{test}")
    public String send(@PathVariable String test) {
        return test;
    }




}
