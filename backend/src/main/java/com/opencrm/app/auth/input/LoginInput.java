package com.opencrm.app.auth.input;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginInput {
    private String email;
    private String password;
}
