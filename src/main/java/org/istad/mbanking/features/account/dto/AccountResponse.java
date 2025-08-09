package org.istad.mbanking.features.account.dto;

import org.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import org.istad.mbanking.features.user.dto.UserResponse;

import java.math.BigDecimal;
import java.util.List;

public record AccountResponse(
        String actNo,
        String actName,
        String alias,
        BigDecimal balance,
        BigDecimal transferLimit,
        AccountTypeResponse accountType,
//        List<UserAccountResponse> userAccountList
        UserResponse user
) {
}
