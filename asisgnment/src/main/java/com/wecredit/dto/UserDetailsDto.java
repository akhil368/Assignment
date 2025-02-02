package com.wecredit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String mobileNumber;
    private String email;
    private String firstName;
    private String lastName;

}
