package com.ecommerce.userservice.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "username must have value")
    @Size(min=1, max=50, message = "username must from 1-50 character.")
    private String username;

    @NotBlank(message = "password must have value")
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String role;
}
