package org.istad.mbanking.features.account.dto;

import org.istad.mbanking.features.user.dto.UserResponse;

import java.util.List;

public record UserAccountResponse(
        Long user,
        Long account
) {
}
