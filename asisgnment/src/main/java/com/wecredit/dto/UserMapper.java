package com.wecredit.dto;

import com.wecredit.model.User;

public class UserMapper {

    public static UserDetailsDto mapToUserDetailsDto(User user) {
        UserDetailsDto UserDetailsDto = new UserDetailsDto();
        UserDetailsDto.setMobileNumber(user.getMobileNumber());
        UserDetailsDto.setEmail(user.getEmail());
        UserDetailsDto.setFirstName(user.getFirstName());
        UserDetailsDto.setLastName(user.getLastName());
        return UserDetailsDto;
    }

    public static User mapToUser(UserDetailsDto UserDetailsDto) {
        User user = new User();
        user.setMobileNumber(UserDetailsDto.getMobileNumber());
        user.setEmail(UserDetailsDto.getEmail());
        user.setFirstName(UserDetailsDto.getFirstName());
        user.setLastName(UserDetailsDto.getLastName());
        return user;
    }
}
