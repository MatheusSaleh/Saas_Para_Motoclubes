package com.mc.saas.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotoRequest(
        @NotNull Long memberId,
        @NotBlank String brand,
        @NotBlank String model,
        Integer manufactureYear,
        String licensePlate,
        String color,
        Integer displacementCc,
        String notes
) {
}
