package com.opencrm.app.api;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.opencrm.app.model.User;
import com.opencrm.app.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final String DEMO_EMAIL = "stanley.hudson@dundermifflin.com01";

    private final UserService userService;

    @QueryMapping
    public User me() {
        User user = userService.getUserByEmail(DEMO_EMAIL);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }
}
