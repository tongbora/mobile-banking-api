package org.istad.mbanking.features.user;

import lombok.RequiredArgsConstructor;
import org.istad.mbanking.base.BaseMessage;
import org.istad.mbanking.domain.Role;
import org.istad.mbanking.domain.User;
import org.istad.mbanking.features.user.dto.*;
import org.istad.mbanking.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public UserDetailsResponse createNew(UserCreateRequest request) {
        if(userRepository.existsByPhoneNumber(request.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone Number is already exists");
        }
        if(userRepository.existsByNationalCardId(request.nationalCardId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National ID is already exists");
        }
        if(userRepository.existsByStudentCardId(request.studentCardId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student ID is already exists");
        }

        if(!request.password().equals(request.confirmPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Passwords are not matching");
        }
        User user = userMapper.fromUserCreateRequest(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setProfileImage("https://cdn-icons-png.flaticon.com/512/6596/6596121.png");

        List<RoleRequest> role = request.roles();
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER").orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Role not found")
        );

        request.roles().forEach(roleRequest -> {
            Role roleEntity = roleRepository.findByName(roleRequest.name()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found")
            );
            roles.add(roleEntity);
        });

        roles.add(userRole);
        user.setRoles(roles);

        return userMapper.toUserDetailsResponse(userRepository.save(user));
    }

    @Override
    public UserDetailsResponse updateByUuid(String uuid, UserUpdateRequest request) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User not found"
                )
        );
        userMapper.updateUserFromUserUpdateRequest(request, user);
        return userMapper.toUserDetailsResponse(userRepository.save(user));
    }

    @Override
    public List<UserDetailsResponse> getAllUser() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDetailsResponse)
                .toList();
    }

    @Override
    public Page<UserDetailsResponse> getAllUserByPage(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
//        Page<User> users = userRepository.findAll(pageRequest);
//        return users.map(
//                userMapper::toUserDetailsResponse
//        );
        return userRepository.findAll(pageRequest).map(
                userMapper::toUserDetailsResponse
        );
    }

    @Override
    public UserResponse getUserByUuid(String uuid) {
        return userMapper.toUserResponse(
                userRepository.findByUuid(uuid).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found."
                        )
                )
        );
    }

    @Override
    public BaseMessage blockByUuid(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found.");
        }
        userRepository.blockByUuid(uuid);
        return new BaseMessage("User have been block.");
    }

    @Override
    public BaseMessage deleteUserByUuid(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found.");
        }
        userRepository.deleteUserByUuid(uuid);
        return new BaseMessage("User has been deleted.");
    }

    @Override
    public BaseMessage disableUserByUuid(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found.");
        }
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if(user.getIsDeleted().equals(true)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User already disble");
        }
        userRepository.disableUserByUuid(uuid);
        return new BaseMessage("User has been disable.");
    }

    @Override
    public BaseMessage enableUserByUuid(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found.");
        }
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if(user.getIsDeleted().equals(false)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User already enable");
        }
        userRepository.enableUserByUuid(uuid);
        return new BaseMessage("User has been enable.");
    }

    @Override
    public String updateProfileImage(String uuid, String mediaName) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        if(user.getIsDeleted().equals(true)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User already disble");
        }
        user.setProfileImage(mediaName);
        userRepository.save(user);
        return baseUri + "IMAGE/" + mediaName;
    }
}
