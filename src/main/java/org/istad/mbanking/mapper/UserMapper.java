package org.istad.mbanking.mapper;

import org.istad.mbanking.domain.User;
import org.istad.mbanking.features.user.dto.UserCreateRequest;
import org.istad.mbanking.features.user.dto.UserDetailsResponse;
import org.istad.mbanking.features.user.dto.UserResponse;
import org.istad.mbanking.features.user.dto.UserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserCreateRequest(UserCreateRequest request);
    UserDetailsResponse toUserDetailsResponse(User user);
    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserUpdateRequest(UserUpdateRequest request, @MappingTarget User user);
}
