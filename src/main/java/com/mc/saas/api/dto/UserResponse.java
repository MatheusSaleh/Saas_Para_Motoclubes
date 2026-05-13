package com.mc.saas.api.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        String photo
) {
}
