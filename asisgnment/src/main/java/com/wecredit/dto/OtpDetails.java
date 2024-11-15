package com.wecredit.dto;

import com.wecredit.enums.OTPStatus;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class OtpDetails {
    private Integer otp;
    private LocalDateTime expiryTime;
    private OTPStatus otpStatus;
    private LocalDateTime verifyLockedTill;
    private LocalDateTime resendLockedTill;
    private Integer otpResendAttemptsLeft;
    private Integer otpVerifyAttemptsLeft;


}
