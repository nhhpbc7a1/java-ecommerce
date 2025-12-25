package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.response.UserResponse;
import com.ecommerce.userservice.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFullName(user.getFullName());
        userResponse.setRole(valueOf(user.getRole()));
        return userResponse;
    }

    public static List<UserResponse> toResponse(List<User> users) {
        List<UserResponse> userResponseList = new ArrayList<>();
        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
