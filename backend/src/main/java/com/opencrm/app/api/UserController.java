package com.opencrm.app.api;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.model.User;
import com.opencrm.app.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @QueryMapping("user")
    public User getUserById(@Argument("id") Long userId) {
        return userService.findById(userId).orElse(null);
    }
}
