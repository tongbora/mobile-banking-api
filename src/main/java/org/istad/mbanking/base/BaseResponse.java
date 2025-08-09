package org.istad.mbanking.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private T payload;
}
