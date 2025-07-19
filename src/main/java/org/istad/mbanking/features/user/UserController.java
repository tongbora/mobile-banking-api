package org.istad.mbanking.features.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.istad.mbanking.base.BaseMessage;
import org.istad.mbanking.features.user.dto.UserCreateRequest;
import org.istad.mbanking.features.user.dto.UserDetailsResponse;
import org.istad.mbanking.features.user.dto.UserResponse;
import org.istad.mbanking.features.user.dto.UserUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUser() {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get All Users Successfully",
                        "users", userService.getAllUser()
                )
        );
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUserByPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "2") int limit
    ) {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get All Users Successfully",
                        "users", userService.getAllUserByPage(page,limit)
                )
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Map<String, Object>> getUserByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get User Successfully",
                        "users", userService.getUserByUuid(uuid)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createNew(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(
                Map.of(
                        "message", "user created",
                        "users", userService.createNew(request)
                )
        );
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<UserDetailsResponse> updateByUuid(@PathVariable String uuid, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(
                userService.updateByUuid(uuid, request)
        );
    }

    @DeleteMapping("/{uuid}/delete")
    public BaseMessage deleteByUuid(@PathVariable String uuid){
        return userService.deleteUserByUuid(uuid);
    }

    @PutMapping("/{uuid}/block")
    public BaseMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuid(uuid);
    }

    @PutMapping("/{uuid}/disable")
    public BaseMessage disableByUuid(@PathVariable String uuid){
        return userService.disableUserByUuid(uuid);
    }

    @PutMapping("/{uuid}/enable")
    public BaseMessage enableByUuid(@PathVariable String uuid){
        return userService.enableUserByUuid(uuid);
    }



}
