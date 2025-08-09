package org.istad.mbanking.features.auth.dto;


public record AuthResponse(
        String token,
        String accessToken,
        String refreshToken
) {
}
