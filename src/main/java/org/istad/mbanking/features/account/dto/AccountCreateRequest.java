package org.istad.mbanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateRequest(
        @NotBlank(message = "The account name is required")
        String alias,
        @NotNull(message = "First Balance is required ($5) up.")
        BigDecimal balance,
        @NotBlank(message = "The account type is required")
        String accountTypeAlias,
        @NotBlank(message = "The user is required")
        String userUuid,

        // optional
        String cardNumber
) {
}
