package org.istad.mbanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRenameRequest(
        @NotBlank(message = "The account name is required")
        @Size(min = 3, max = 255, message = "The account name must be between 3 and 255 characters long")
        String newName
) {
}
