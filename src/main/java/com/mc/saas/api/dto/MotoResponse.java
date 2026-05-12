package com.mc.saas.api.dto;

public record MotoResponse(
        Long id,
        Long memberId,
        String memberPersonFullName,
        String brand,
        String model,
        Integer manufactureYear,
        String licensePlate,
        String color,
        Integer displacementCc,
        String notes
) {
}
