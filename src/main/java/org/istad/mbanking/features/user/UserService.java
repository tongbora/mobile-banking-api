package org.istad.mbanking.features.user;

import org.istad.mbanking.features.user.dto.UserCreateRequest;

public interface UserService {

    void createNew(UserCreateRequest request);
}
