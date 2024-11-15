package com.wecredit.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class OTPAuthenticationToken extends AbstractAuthenticationToken {

    private final String mobileNumber;
    private final Integer otp;

    public OTPAuthenticationToken(String mobileNumber, Integer otp) {
        super(null);
        this.mobileNumber = mobileNumber;
        this.otp = otp;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return otp;
    }

    @Override
    public Object getPrincipal() {
        return mobileNumber;
    }
}

