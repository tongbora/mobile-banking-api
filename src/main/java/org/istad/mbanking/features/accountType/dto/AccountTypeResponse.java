package org.istad.mbanking.features.accountType.dto;

import jakarta.persistence.Column;

public record AccountTypeResponse(
        String alias,
        String name,
        String description
) {
}
