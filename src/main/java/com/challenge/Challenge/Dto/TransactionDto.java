package com.challenge.Challenge.Dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionDto(
        @DecimalMin(value = "0.01", message = "Valor must be greater than zero")
        BigDecimal valor,
        @PastOrPresent(message = "Date must be in the past")
        OffsetDateTime dataHora
) {
}