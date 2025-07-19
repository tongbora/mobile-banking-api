package org.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

public record UserCreateRequest(
        @NotNull
//        @Size(max = 20)
        String nationalCardId,

        @Digits(integer = 4, fraction = 0)
        @Positive
        Integer pin,

        @NotBlank
        @Size(min = 8, max = 15)
        String phoneNumber,

        @NotBlank
        String password,

        @NotBlank
        String confirmPassword,

        @NotBlank
        @Size(max = 40)
        String name,

        @NotBlank
        @Size(max = 6)
        String gender,

        @NotNull
        LocalDate dob,

        @Size(max = 20)
        String studentCardId,

        @NotEmpty
        List<RoleRequest> roles
) {
}
