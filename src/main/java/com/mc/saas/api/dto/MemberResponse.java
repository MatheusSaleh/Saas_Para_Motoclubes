package com.mc.saas.api.dto;

public record MemberResponse(
        Long id,
        Long personId,
        String personFullName,
        Long spouseId,
        String spouseFullName,
        String cpf,
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
