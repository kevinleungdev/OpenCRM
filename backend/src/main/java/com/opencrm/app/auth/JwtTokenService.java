package com.opencrm.app.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    public final Integer ACCESS_TOKEN_EXPIRATION_IN_MINUTES = 30;
    public final Integer REFRESH_TOKEN_EXPIRATION_IN_MINUTES = 60;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateAccessToken(Authentication authentication) {
        return this.generateToken(authentication.getName(), authentication.getAuthorities(),
                Instant.now().plus(ACCESS_TOKEN_EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES));
    }

    public String generateRefreshToken(Authentication authentication) {
        return this.generateToken(authentication.getName(), List.of(),
                Instant.now().plus(REFRESH_TOKEN_EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES));
    }

    public String generateAccessToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return this.generateToken(username, authorities,
                Instant.now().plus(ACCESS_TOKEN_EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES));
    }

    public String generateRefreshToken(String username) {
        return this.generateToken(username, List.of(),
                Instant.now().plus(REFRESH_TOKEN_EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES));
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities,
            Instant expiresAt) {
        String scope = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("opencrm")
                .issuedAt(Instant.now())
                .expiresAt(expiresAt)
                .subject(username)
                .claim("scope", scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt parseToken(String token) {
        return jwtDecoder.decode(token);
    }
}
