package org.istad.mbanking.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseError<T> {

    // status code {404: not found}
    private String code;

    // Detail error for user
    private T description;
}
