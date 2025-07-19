package org.istad.mbanking.features.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record UserDetailsResponse(
        String uuid,
        String name,
        String profileImage,
        String gender,
        LocalDate dob,
        String cityOrProvince,
        String khanOrDistrict,
        String village,
        String street,
        String employeeType,
        String position,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRance,
        String studentCardId,
        List<RoleResponse> roles
) {
}
