package com.opencrm.app.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;

    public JwtTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateAccessToken(Authentication authentication) {
        return this.generateToken(authentication.getName(), authentication.getAuthorities(),
                Instant.now().plus(30, ChronoUnit.MINUTES));
    }

    public String generateRefreshToken(Authentication authentication) {
        return this.generateToken(authentication.getName(), List.of(),
                Instant.now().plus(60, ChronoUnit.MINUTES));
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
}
