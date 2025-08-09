package org.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserProfileImageRequest(
        @NotBlank(message = "The media name is required")
        String mediaName
) {
}
