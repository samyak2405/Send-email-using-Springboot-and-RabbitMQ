package com.javahunter.emailnotification.mapper;

import com.javahunter.emailnotification.entity.User;
import com.javahunter.emailnotification.payload.RequestDto;
import com.javahunter.emailnotification.payload.UserDetailsDto;

public class UserMapper {

    public static User mapToUser(User user, RequestDto requestDto){
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        return user;
    }

    public static UserDetailsDto mapToUserDetails(UserDetailsDto userDetailsDto,User user){
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setLastName(user.getLastName());
        userDetailsDto.setEmail(user.getEmail());
        return userDetailsDto;
    }
}
