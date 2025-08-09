package org.istad.mbanking.features.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.istad.mbanking.features.auth.dto.AuthResponse;
import org.istad.mbanking.features.auth.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
