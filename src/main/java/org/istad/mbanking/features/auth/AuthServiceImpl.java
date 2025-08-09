package org.istad.mbanking.features.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mbanking.features.auth.dto.AuthResponse;
import org.istad.mbanking.features.auth.dto.LoginRequest;
import org.istad.mbanking.security.CustomUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        // This will check authentication
        auth = daoAuthenticationProvider.authenticate(auth);

        CustomUserDetail user = (CustomUserDetail) auth.getPrincipal();
        log.info("username: {}", user.getUsername());
        log.info("password: {}", user.getPassword());
//        log.info("Authorities {}", user.getAuthorities());
        user.getAuthorities().forEach(
                authority -> log.info("Authority: {}", authority.getAuthority())
        );

        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(user.getUsername())
                .subject("Access Token")
                .issuedAt(now)
                .expiresAt(now.plus(5 , ChronoUnit.MINUTES))
                .audience(List.of("Web"))
                .issuer(user.getUsername())
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return new AuthResponse(
                "Bearer",
                accessToken,
                ""
        );
    }
}
