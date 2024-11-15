package com.wecredit.service;

import com.wecredit.config.JwtUtil;
import com.wecredit.dto.OtpDetails;
import com.wecredit.enums.OTPStatus;
import com.wecredit.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OtpService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public String generateOtp(String phoneNumber) {
        User user= userService.findUserByPhoneNumber(phoneNumber);
        resetOTPAttemptsAndLocks(user);
        int lowerLimit = (int) Math.pow(10, 4 - 1);
        int upperLimit = lowerLimit * 10;
        Integer otp = ((int) (Math.random() * (upperLimit - lowerLimit) + lowerLimit));

        if(Objects.nonNull(user.getOtpResendAttemptsLeft()) &&  user.getOtpResendAttemptsLeft()==0  ) {
            return OTPStatus.OTP_LIMIT_REACHED.toString();
        }
        if(Objects.isNull(user.getOtp())) {
            user.setOtp(otp);
            user.setExpiryTime(LocalDateTime.now().plusMinutes(1));
            user.setOtpStatus(OTPStatus.OTP_GENERATED);
            user.setOtpResendAttemptsLeft(2);
            user.setVerifyLockedTill(null);
            user.setOtpVerifyAttemptsLeft(3);
            user.setExpiryTime(LocalDateTime.now().plusMinutes(1));
        } else {
            user.setOtp(otp);
            user.setOtpStatus(OTPStatus.OTP_GENERATED);
            user.setExpiryTime(LocalDateTime.now().plusMinutes(1));
            if(user.getOtpVerifyAttemptsLeft()==0 ) {
                user.setVerifyLockedTill(LocalDateTime.now().plusMinutes(2));
            }
            user.setOtpVerifyAttemptsLeft(user.getOtpResendAttemptsLeft()>0?user.getOtpResendAttemptsLeft()-1:0);
            if(user.getOtpResendAttemptsLeft()==0) {
                user.setResendLockedTill(LocalDateTime.now().plusMinutes(2));
            }
        }
        userService.saveUser(user);
        return user.getOtp().toString();

    }

    public String verifyOtp(String phoneNumber, Integer otp) {
        User user= userService.findUserByPhoneNumber(phoneNumber);
        if (LocalDateTime.now().isAfter(user.getExpiryTime())) {
            user.setOtpStatus(OTPStatus.OTP_EXPIRED);
        }
        else if (user.getOtp().equals(otp) ) {
            String token=jwtUtil.generateToken(user);
            System.out.println("Token: "+token);
            user.setIsVerified(true);
            user.setOtpStatus(OTPStatus.OTP_VERIFIED);
        } else {
            user.setOtpStatus(OTPStatus.OTP_MISMATCH);
        }

        if (user.getOtpVerifyAttemptsLeft() > 0) {
            user.setOtpVerifyAttemptsLeft(user.getOtpVerifyAttemptsLeft() - 1);
            if (user.getOtpVerifyAttemptsLeft() == 0) {
                user.setVerifyLockedTill(LocalDateTime.now().plusHours(1));
            }
        }
        userService.saveUser(user);
        return user.getOtpStatus().toString();
    }

    public void resetOTPAttemptsAndLocks(User user) {
        if (Objects.nonNull(user.getVerifyLockedTill()) &&
                user.getVerifyLockedTill().isBefore(
                        LocalDateTime.now())) {
            user.setOtpVerifyAttemptsLeft(3);
            user.setVerifyLockedTill(null);
        }
        if (Objects.nonNull(user.getResendLockedTill()) &&
                user.getResendLockedTill().isBefore(
                        LocalDateTime.now())) {
            user.setOtpResendAttemptsLeft(2);
            user.setResendLockedTill(null);
        }
    }

}
