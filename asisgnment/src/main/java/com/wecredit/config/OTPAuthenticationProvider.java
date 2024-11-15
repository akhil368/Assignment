package com.wecredit.config;

import com.wecredit.model.User;
import com.wecredit.repository.UserRepository;
import com.wecredit.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Configuration
public class OTPAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;


    public String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobileNumber = authentication.getName();
        Integer otp = (Integer) authentication.getCredentials();


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientIP = getClientIP(request);
        String otpValid = otpService.verifyOtp(mobileNumber, otp);
        if (!otpValid.equals("OTP_VERIFIED")) {
            throw new BadCredentialsException(otpValid);
        }

        User user = userRepository.findUserByMobileNumber(mobileNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (Objects.isNull(user.getDeviceID())  || !user.getDeviceID().equals(clientIP)) {
            throw new BadCredentialsException("FingerPrint Mismatched ");
        }
        String token = jwtUtil.generateToken(user);
        System.out.println("token :" + token);
        return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

