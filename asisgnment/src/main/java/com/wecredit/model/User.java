package com.wecredit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wecredit.enums.OTPStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Mobile Number should not be blank")
    @Size(min = 10, message = "Mobile Number must be of 10 Digits")
    @Column(nullable = false)
    private String mobileNumber;

    private String deviceID;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotBlank(message = "First Name should not be blank")
    private String firstName;

    private String lastName;

    private String email;

    private Integer otp;

    private Boolean isOtpExpired;

    private Integer otpResendAttemptsLeft;

    private Boolean isVerified;

    private LocalDateTime expiryTime;

    private LocalDateTime verifyLockedTill;
    private LocalDateTime resendLockedTill;
    private Integer otpVerifyAttemptsLeft;
    private OTPStatus otpStatus;



    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.mobileNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
