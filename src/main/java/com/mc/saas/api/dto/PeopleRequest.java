package com.mc.saas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record PeopleRequest(@NotBlank String fullName, String photo) {
}
