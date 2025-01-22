package com.opencrm.app.auth.output;

import com.opencrm.app.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private User user;
}
