package org.istad.mbanking.features.auth;


import org.istad.mbanking.features.auth.dto.AuthResponse;
import org.istad.mbanking.features.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}
