package org.istad.mbanking.mapper;

import org.istad.mbanking.domain.User;
import org.istad.mbanking.features.user.dto.UserCreateRequest;
import org.istad.mbanking.features.user.dto.UserDetailsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserCreateRequest(UserCreateRequest request);
    UserDetailsResponse toUserDetailsResponse(User user);
}
