package org.istad.mbanking.features.user;

import lombok.RequiredArgsConstructor;
import org.istad.mbanking.features.user.dto.UserCreateRequest;
import org.istad.mbanking.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void createNew(UserCreateRequest request) {
        userRepository.save(userMapper.fromUserCreateRequest(request));
    }
}
