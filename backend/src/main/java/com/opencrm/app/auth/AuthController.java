package com.opencrm.app.auth;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import com.opencrm.app.auth.input.LoginInput;
import com.opencrm.app.auth.output.AuthResponse;
import com.opencrm.app.model.User;
import com.opencrm.app.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @PreAuthorize("isAnonymous()")
    @MutationMapping
    public AuthResponse login(@Argument LoginInput loginInput) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginInput.getEmail(),
                loginInput.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        String accessToken = jwtTokenService.generateAccessToken(authentication);
        String refreshToken = jwtTokenService.generateRefreshToken(authentication);

        User user = userService.getUserByEmail(loginInput.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user).build();
    }

    @PreAuthorize("isAnonymous()")
    @MutationMapping
    public AuthResponse refreshToken(String refreshToken) {
        Jwt jwt = jwtTokenService.parseToken(refreshToken);

        String username = jwt.getSubject();
        log.info("username: {}", username);

        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }

        String newAccessToken = jwtTokenService.generateAccessToken(username, user.getAuthorities());
        String newRefreshToken = jwtTokenService.generateRefreshToken(username);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .user(user)
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public User me(@AuthenticationPrincipal(errorOnInvalidType = true) Jwt jwt) {
        String username = jwt.getSubject();
        log.info("jwt subject(username): {}", username);

        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Usern {} not found!", username));
        }

        return user;
    }
}
