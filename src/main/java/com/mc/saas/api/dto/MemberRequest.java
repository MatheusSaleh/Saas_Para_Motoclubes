package com.mc.saas.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberRequest(
        @NotNull Long personId,
        Long spouseId,
        @NotBlank String cpf,
        String rg,
        String country,
        String occupation,
        boolean active,
        boolean upToDate,
        String alias,
        String bloodType,
        String phone,
        String email,
        boolean authorizedTheUseOfMedia
) {
}
