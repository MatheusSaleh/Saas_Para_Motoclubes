package com.mc.saas.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequest(
        @NotNull Long memberId,
        LocalDate paymentDate,
        @NotBlank String monthYearReference,
        @NotNull BigDecimal amount,
        @NotBlank String paymentMethod,
        @NotBlank String paymentStatus,
        @NotNull LocalDate dueDate
) {
}
