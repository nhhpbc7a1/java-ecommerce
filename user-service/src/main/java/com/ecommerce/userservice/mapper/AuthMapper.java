package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.response.AuthResponse;
import com.ecommerce.userservice.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class AuthMapper {
    public static AuthResponse toResponse(User user) {
        AuthResponse userResponse = new AuthResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFullName(user.getFullName());
        userResponse.setRole(user.getRole() != null ? user.getRole().name() : "USER");
        return userResponse;
    }

    public static List<AuthResponse> toResponse(List<User> users) {
        List<AuthResponse> userResponseList = new ArrayList<>();
        return users.stream()
                .map(AuthMapper::toResponse)
                .collect(Collectors.toList());
    }
}