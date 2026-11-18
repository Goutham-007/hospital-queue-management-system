package com.hqms.auth.dto;

public record AuthenticatedUser(Long id, String email, String role) {}
