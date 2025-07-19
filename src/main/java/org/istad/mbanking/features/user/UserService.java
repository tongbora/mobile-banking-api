package org.istad.mbanking.features.user;

import org.istad.mbanking.base.BaseMessage;
import org.istad.mbanking.features.user.dto.UserCreateRequest;
import org.istad.mbanking.features.user.dto.UserDetailsResponse;
import org.istad.mbanking.features.user.dto.UserResponse;
import org.istad.mbanking.features.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserDetailsResponse createNew(UserCreateRequest request);

    UserDetailsResponse updateByUuid(String uuid, UserUpdateRequest request);

    List<UserDetailsResponse> getAllUser();
    Page<UserDetailsResponse> getAllUserByPage(int page, int limit);
    UserResponse getUserByUuid(String uuid);

    BaseMessage blockByUuid(String uuid);
    BaseMessage deleteUserByUuid(String uuid);
    BaseMessage disableUserByUuid(String uuid);
    BaseMessage enableUserByUuid(String uuid);
}
