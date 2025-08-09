package org.istad.mbanking.features.mail.dto;

import jakarta.validation.constraints.NotNull;

public record MailRequest(
        @NotNull(message = "Please specify at least one recipient.")
        String to,

        String subject,

        String body
) {
}
