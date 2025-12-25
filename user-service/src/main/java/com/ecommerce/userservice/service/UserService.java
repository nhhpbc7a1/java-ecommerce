package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.request.LoginRequest;
import com.ecommerce.userservice.dto.request.RegisterRequest;
import com.ecommerce.userservice.dto.response.AuthResponse;
import com.ecommerce.userservice.dto.response.UserResponse;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.entity.UserRole;
import com.ecommerce.userservice.mapper.AuthMapper;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.hibernate.engine.spi.Resolution;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public AuthResponse register(@Valid RegisterRequest request) {
        User user = new User();
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("This Email had an account before.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("This Username had an account before.");
        }

        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(UserRole.USER);

        if (request.getRole() != null && !request.getRole().isEmpty()) {
            try {
                user.setRole(UserRole.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + request.getRole());
            }
        } else {
            user.setRole(UserRole.USER);
        }

        return AuthMapper.toResponse(userRepository.save(user));
    }

    public AuthResponse login(@Valid LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResolutionException("Username not found."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResolutionException("Wrong password.");
        }
        return AuthMapper.toResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResolutionException("User not found."));
        return UserMapper.toResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResolutionException("User not found."));
        return UserMapper.toResponse(user);
    }
}
