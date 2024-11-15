package com.wecredit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class WeCreditUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String mobileNumber;
    private String deviceID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String firstName;
    private String lastName;
    private String email;
    private Integer otp;
    private Boolean isOtpExpired;
    private Integer otpRetryCount;
    private LocalDateTime otpLockedTill;


}
