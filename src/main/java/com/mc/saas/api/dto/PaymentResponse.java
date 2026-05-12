package com.mc.saas.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(
        Long id,
        Long memberId,
        String memberPersonFullName,
        LocalDate paymentDate,
        String monthYearReference,
        BigDecimal amount,
        String paymentMethod,
        String paymentStatus,
        LocalDate dueDate
) {
}
