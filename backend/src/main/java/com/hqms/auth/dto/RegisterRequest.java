package com.hqms.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank
        @Size(max = 100)
        String fullName,
        @NotBlank
        @Email
        String email,
        @Pattern(regexp = "^[0-9]{10}$", message = "phone must be 10 digits")
        String phone,
        @NotBlank
        @Size(min = 8, max = 72)
        String password) {}
