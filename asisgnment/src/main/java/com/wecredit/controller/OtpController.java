package com.wecredit.controller;

import com.wecredit.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/sendOtp/{phoneNumber}")
    public ResponseEntity<String> sendOtp(@PathVariable String phoneNumber) {
        try {
            return ResponseEntity.ok(otpService.generateOtp(phoneNumber));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/verifyOtp/{phoneNumber}/{otp}")
    public ResponseEntity<String> verifyOtp(@PathVariable String phoneNumber, @PathVariable Integer otp) {
        try {
            return ResponseEntity.ok(otpService.verifyOtp(phoneNumber, otp));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

}
