package org.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

public record UserCreateRequest(
        @NotNull (message = "The national card id is required")
//        @Size(max = 20)
        String nationalCardId,

        @Digits(integer = 4, fraction = 0, message = "The pin must be a number")
        @Positive
        @NotNull(message = "The pin is required")
        Integer pin,

        @NotBlank(message = "The phone number is required")
        @Size(min = 8, max = 15, message = "The phone number must be between 8 and 15 characters")
        String phoneNumber,

        @NotBlank(message = "The password is required")
        String password,

        @NotBlank(message = "The confirm password is required")
        String confirmPassword,

        @NotBlank(message = "The name is required")
        @Size(max = 40)
        String name,

        @NotBlank(message = "The gender is required")
        @Size(max = 6)
        String gender,

        @NotNull(message = "The date of birth is required")
        LocalDate dob,

        @Size(max = 20)
        String studentCardId,

        @NotEmpty(message = "At least one role is required")
        List<RoleRequest> roles
) {
}
